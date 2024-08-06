package tamlagukbe.tamlagukbe.Review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tamlagukbe.tamlagukbe.Review.dto.TranscriptRecord;
import tamlagukbe.tamlagukbe.Review.dto.TranscriptResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TranscriptService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private RestTemplate restTemplate;

    private List<TranscriptRecord> records = new ArrayList<>();

    public TranscriptRecord transcribe(MultipartFile audioFile) throws IOException {
        // 파일 저장
        String fileName = saveFile(audioFile);

        // 오디오 파일을 Base64 인코딩
        String base64Audio = Base64.getEncoder().encodeToString(audioFile.getBytes());

        // 요청을 위한 MultiValueMap 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("audio", base64Audio);

        // OpenAI API 호출
        TranscriptResponse response = restTemplate.postForObject(apiUrl + "/transcriptions", body, TranscriptResponse.class);

        // 감정 평가 호출
        String sentiment = evaluateSentiment(response.getTranscript());

        // 결과 저장
        TranscriptRecord record = new TranscriptRecord(fileName, response.getTranscript(), sentiment);
        records.add(record);

        return record;
    }

    public List<TranscriptRecord> getRecords() {
        return records;
    }

    private String saveFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath + File.separator + file.getOriginalFilename());
        Files.write(path, bytes);
        return file.getOriginalFilename();
    }

    private String evaluateSentiment(String text) {
        String prompt = "Evaluate the sentiment of the following text as POSITIVE or NEGATIVE:\n\n" + text;
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("model", "text-davinci-003");
        body.add("prompt", prompt);
        body.add("max_tokens", "1");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + apiKey);

        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("headers", headers);
        request.add("body", body);

        @SuppressWarnings("unchecked")
        List<LinkedHashMap<String, String>> choices = (List<LinkedHashMap<String, String>>) restTemplate.postForObject(apiUrl + "/completions", request, LinkedHashMap.class).get("choices");

        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).get("text").trim();
        } else {
            return "NEUTRAL";
        }
    }
}