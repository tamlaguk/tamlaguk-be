package tamlagukbe.tamlagukbe.Review.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tamlagukbe.tamlagukbe.Review.dto.FoodReviewDto;
import tamlagukbe.tamlagukbe.Review.entity.FoodReview;
import tamlagukbe.tamlagukbe.Review.repository.FoodReviewRepository;
import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import tamlagukbe.tamlagukbe.foodselect.repository.FoodRepository;
import tamlagukbe.tamlagukbe.global.util.aws.service.AwsS3Service;
import tamlagukbe.tamlagukbe.member.entity.Member;
import tamlagukbe.tamlagukbe.member.repository.MemberRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodReviewService {
    @Value("${openai.api.transcription-url}")
    private String transcriptionUrl;

    @Value("${openai.api.chat-url}")
    private String chatUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final FoodReviewRepository foodReviewRepository;
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    private final AwsS3Service awsS3Service;

    public FoodReviewDto transcribe(MultipartFile audioFile, Long userId, Long foodStoreId) throws IOException {
        // S3에 음성 파일 저장 -> URL 반환
        String audioUrl = uploadAudioUrl(audioFile);

        // OpenAI API 호출하여 음성 파일을 텍스트로 변환
        String transcript = transcribeAudio(audioFile);

        // 감정 평가 호출
        String sentiment = evaluateSentiment(transcript);

        // Member 및 Food 엔터티를 가져오기
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Food food = foodRepository.findById(foodStoreId)
                .orElseThrow(() -> new RuntimeException("Food store not found"));

        // 결과 저장
        FoodReview review = new FoodReview();
        review.setUserId(member);
        review.setFoodId(food); // Food 엔터티를 설정
        review.setAudioUrl(audioUrl);
        review.setContent(transcript);
        review.setSentiment(sentiment);
        review.setCreatedAt(LocalDateTime.now());

        if ("POSITIVE".equalsIgnoreCase(sentiment)) {
            foodReviewRepository.save(review);
        }

        return convertToDto(review);
    }

    public FoodReviewDto getReviewById(Long reviewId) {
        FoodReview review = foodReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return convertToDto(review);
    }

    public List<FoodReviewDto> getReviews() {
        return foodReviewRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FoodReviewDto convertToDto(FoodReview review) {
        return FoodReviewDto.builder()
                .id(review.getId())
                .userId(review.getUserId().getId())
                .foodId(review.getFoodId().getId())
                .foodStoreName(review.getFoodId().getFoodStoreName())
                .audioUrl(review.getAudioUrl())
                .content(review.getContent())
                .sentiment(review.getSentiment())
                .createdAt(review.getCreatedAt())
                .build();
    }

    private String transcribeAudio(MultipartFile audioFile) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", audioFile.getResource());
        body.add("model", "whisper-1");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        Map<String, Object> response = restTemplate.postForObject(transcriptionUrl, request, Map.class);
        return (String) response.get("transcript");
    }

    private String evaluateSentiment(String text) {
        String prompt = "Evaluate the sentiment of the following text as POSITIVE or NEGATIVE. Respond only in JSON format. Do not provide any explanations.\n" +
                "```json\n" +
                "{\n" +
                "\"변환 텍스트\": \"" + text + "\",\n" +
                "\"감정분석결과\": \"\"\n" +
                "}\n" +
                "```";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", "gpt-3.5-turbo");
        body.add("messages", List.of(
                Map.of("role", "system", "content", "You are a sentiment analysis assistant."),
                Map.of("role", "user", "content", prompt)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        Map<String, Object> response = restTemplate.postForObject(chatUrl, request, Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");

        if (choices != null && !choices.isEmpty()) {
            String jsonResponse = (String) choices.get(0).get("message");
            Map<String, String> result = new Gson().fromJson(jsonResponse, Map.class);
            return result.get("감정분석결과");
        } else {
            return "NEUTRAL";
        }
    }

    /**
     * 파일을 AWS S3에 업로드하고 그에 대한 URL 을 반환합니다.
     * @param multipartFile 업로드할 파일
     * @return 업로드된 파일의 S3 URL
     */
    private String uploadAudioUrl(MultipartFile multipartFile) {
        String fileName = awsS3Service.generateFileName(multipartFile);
        awsS3Service.uploadToS3(multipartFile, fileName);
        return awsS3Service.getUrl(fileName);
    }
}