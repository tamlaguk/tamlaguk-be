package tamlagukbe.tamlagukbe.Review.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "place_review")
public class PlaceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place placeReview;

    @Column(name = "audioUrl", nullable = false, length = 255)
    private String audioUrl;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "sentiment", nullable = false, length = 25)
    private String sentiment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
