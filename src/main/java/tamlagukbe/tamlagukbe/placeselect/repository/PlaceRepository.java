package tamlagukbe.tamlagukbe.placeselect.repository;

import tamlagukbe.tamlagukbe.placeselect.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 카테고리에 따라 Place 엔티티 목록을 찾는 메서드
    List<Place> findByCategory(String category);
}
