package tamlagukbe.tamlagukbe.Review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranscriptResponse {
    @JsonProperty("transcript")
    private String transcript;

    // 추가 응답 필드가 필요하면 여기에 추가합니다.

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }
}
