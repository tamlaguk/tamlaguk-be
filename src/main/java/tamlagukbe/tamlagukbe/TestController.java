package tamlagukbe.tamlagukbe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

      @GetMapping("api/test")
      public ResponseEntity<?> test() {
            return ResponseEntity.ok("test");
      }

      @GetMapping("api/test2")
      public ResponseEntity<?> test2() {
            return ResponseEntity.ok("test");
      }

      @GetMapping("api/ext")
      public ResponseEntity<?> ext() {
            return ResponseEntity.ok("ext");
      }
}
