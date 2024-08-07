package tamlagukbe.tamlagukbe.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityReviewDto {

    private Long id;
    private Long userId;
    private Long placeId;
    private String audioUrl;
    private String content;
    private String sentiment;
    private LocalDateTime createdAt;
}
