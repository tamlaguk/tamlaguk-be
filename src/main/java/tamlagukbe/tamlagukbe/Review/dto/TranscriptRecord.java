package tamlagukbe.tamlagukbe.Review.dto;

public class TranscriptRecord {
    private String fileName;
    private String transcript;
    private String sentiment;

    public TranscriptRecord(String fileName, String transcript, String sentiment) {
        this.fileName = fileName;
        this.transcript = transcript;
        this.sentiment = sentiment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
}
