package tamlagukbe.tamlagukbe.global.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import tamlagukbe.tamlagukbe.global.exception.type.ErrorCode;

@Data
@AllArgsConstructor
public class ErrorResponse {

      private ErrorCode errorCode;
      private HttpStatus httpStatus;
      private String errorMessage;
}
