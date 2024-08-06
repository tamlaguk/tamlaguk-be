package tamlagukbe.tamlagukbe.activityselect.repository;

import tamlagukbe.tamlagukbe.activityselect.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // 카테고리에 따라 Activity 엔티티 목록을 찾는 메서드
    List<Activity> findByCategory(String category);
}
