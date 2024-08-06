package tamlagukbe.tamlagukbe.global.exception;

import lombok.Getter;
import tamlagukbe.tamlagukbe.global.exception.type.ErrorCode;

@Getter
public class GlobalException extends RuntimeException {

      private final ErrorCode errorCode;

      public GlobalException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
      }
}
