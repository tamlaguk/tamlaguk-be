package tamlagukbe.tamlagukbe.Review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tamlagukbe.tamlagukbe.Review.dto.TranscriptResponse;
import tamlagukbe.tamlagukbe.Review.entity.FoodReview;
import tamlagukbe.tamlagukbe.Review.repository.FoodReviewRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodReviewService {
    @Value("${openai.api.transcription-url}")
    private String transcriptionUrl;

    @Value("${openai.api.chat-url}")
    private String chatUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FoodReviewRepository foodReviewRepository;

    public FoodReview transcribe(MultipartFile audioFile, Long userId, Long placeId) throws IOException {
        // 파일 저장
        String fileName = saveFile(audioFile);

        // OpenAI API 호출하여 음성 파일을 텍스트로 변환
        String transcript = transcribeAudio(audioFile);

        // 감정 평가 호출
        String sentiment = evaluateSentiment(transcript);

        // 결과 저장
        FoodReview review = new FoodReview();
        review.setUserId(userId);
        review.setPlaceId(placeId);
        review.setAudioUrl(uploadPath + "/" + fileName);
        review.setContent(transcript);
        review.setSentiment(sentiment);
        review.setCreatedAt(LocalDateTime.now());

        if ("POSITIVE".equalsIgnoreCase(sentiment)) {
            foodReviewRepository.save(review);
        }

        return review;
    }

    public List<FoodReview> getReviews() {
        return foodReviewRepository.findAll();
    }

    private String saveFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath + "/" + file.getOriginalFilename());
        Files.write(path, bytes);
        return file.getOriginalFilename();
    }

    private String transcribeAudio(MultipartFile audioFile) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", audioFile.getResource());
        body.add("model", "whisper-1");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + apiKey);

        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("headers", headers);
        request.add("body", body);

        TranscriptResponse response = restTemplate.postForObject(transcriptionUrl, request, TranscriptResponse.class);
        return response.getTranscript();
    }

    private String evaluateSentiment(String text) {
        String prompt = "Evaluate the sentiment of the following text as POSITIVE or NEGATIVE:\n\n" + text;
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", "gpt-3.5-turbo");
        body.add("messages", List.of(
                new LinkedMultiValueMap<String, String>() {{
                    add("role", "system");
                    add("content", "You are a sentiment analysis assistant.");
                }},
                new LinkedMultiValueMap<String, String>() {{
                    add("role", "user");
                    add("content", prompt);
                }}
        ));

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + apiKey);

        Map<String, Object> response = restTemplate.postForObject(chatUrl, new HttpEntity<>(body, headers), Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");

        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).get("message").toString().trim();
        } else {
            return "NEUTRAL";
        }
    }
}
