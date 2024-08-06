package tamlagukbe.tamlagukbe.activityselect.service;

import tamlagukbe.tamlagukbe.activityselect.entity.Activity;
import tamlagukbe.tamlagukbe.activityselect.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity getRandomActivityByCategory(String category) {
        List<Activity> activities = activityRepository.findByCategory(category);
        if (activities.isEmpty()) {
            return null; // 카테고리에 해당하는 활동이 없는 경우
        }
        Random random = new Random();
        int randomIndex = random.nextInt(activities.size());
        return activities.get(randomIndex); // 무작위로 활동 선택
    }
}
