package tamlagukbe.tamlagukbe.Review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tamlagukbe.tamlagukbe.activityselect.entity.Activity;
import tamlagukbe.tamlagukbe.member.entity.Member;
import tamlagukbe.tamlagukbe.placeselect.entity.Place;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity_review")
public class ActivityReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member userId;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activityId;

    @Column(name = "audioUrl", nullable = false, length = 255)
    private String audioUrl;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "sentiment", nullable = false, length = 25)
    private String sentiment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
