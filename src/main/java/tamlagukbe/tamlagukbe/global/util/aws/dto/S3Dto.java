package tamlagukbe.tamlagukbe.global.util.aws.dto;

import lombok.Builder;


@Builder
public record S3Dto(
        Long id,
        String url,
        String fileName,
        Long size
) {
      
}
