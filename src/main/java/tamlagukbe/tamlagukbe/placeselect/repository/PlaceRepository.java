package tamlagukbe.tamlagukbe.placeselect.repository;

import tamlagukbe.tamlagukbe.placeselect.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByExistsFree(String existsFree); // existsFree 필드에 대한 메서드 추가
}
