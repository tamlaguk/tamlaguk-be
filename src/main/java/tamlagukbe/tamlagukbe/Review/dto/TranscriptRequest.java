package tamlagukbe.tamlagukbe.Review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranscriptRequest {
    @JsonProperty("audio")
    private String audio;

    // 추가 파라미터가 필요하면 여기에 추가합니다.

    public TranscriptRequest(String audio) {
        this.audio = audio;
    }
}
