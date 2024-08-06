package tamlagukbe.tamlagukbe.Review.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tamlagukbe.tamlagukbe.Review.dto.TranscriptRecord;
import tamlagukbe.tamlagukbe.Review.dto.TranscriptResponse;
import tamlagukbe.tamlagukbe.Review.service.TranscriptService;

import java.io.IOException;
import java.util.List;

@RestController
public class TranscriptController {

    @Autowired
    private TranscriptService transcriptionService;
    @Autowired
    private TranscriptService transcriptService;

    @PostMapping("/transcribe")
    public TranscriptRecord transcribe(@RequestParam("file") MultipartFile file) throws IOException {
        return transcriptService.transcribe(file);
    }

    @GetMapping("/transcriptions")
    public List<TranscriptRecord> getTranscriptions() {
        return transcriptService.getRecords();
    }
}
