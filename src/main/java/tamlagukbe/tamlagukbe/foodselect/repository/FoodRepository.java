package tamlagukbe.tamlagukbe.foodselect.repository;

import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    // 카테고리에 따라 Food 엔티티 목록을 찾는 메서드
    List<Food> findByCategory(String category);
}
