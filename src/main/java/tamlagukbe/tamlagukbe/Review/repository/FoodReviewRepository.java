package tamlagukbe.tamlagukbe.Review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tamlagukbe.tamlagukbe.Review.entity.FoodReview;

public interface FoodReviewRepository extends JpaRepository<FoodReview, Long> {
}
