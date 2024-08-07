package tamlagukbe.tamlagukbe.foodselect.repository;

import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
}
