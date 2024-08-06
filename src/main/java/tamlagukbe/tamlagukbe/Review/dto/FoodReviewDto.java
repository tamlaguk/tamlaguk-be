package tamlagukbe.tamlagukbe.Review.dto;

import java.time.LocalDateTime;

public class FoodReviewDto {

    private Long id;
    private Long userId;
    private Long placeId;
    private String audioUrl;
    private String content;
    private String sentiment;
    private LocalDateTime createdAt;
}
