package tamlagukbe.tamlagukbe.activityselect.service;

import tamlagukbe.tamlagukbe.activityselect.entity.Activity;
import tamlagukbe.tamlagukbe.activityselect.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Random;

@Service
public class ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepository;

    public Activity getRandomActivityByCategory(String category) {
        // URL 디코딩 수행
        String decodedCategory;
        try {
            decodedCategory = URLDecoder.decode(category, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding category: {}", category, e);
            return null;
        }
        logger.info("Encoded category: {}", category);
        logger.info("Decoded category: {}", decodedCategory);

        // 디코딩된 카테고리를 사용하여 활동 검색
        List<Activity> activities = activityRepository.findByCategory(decodedCategory);
        if (activities.isEmpty()) {
            logger.warn("No activity found for category: {}", decodedCategory);
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(activities.size());
        Activity selectedActivity = activities.get(randomIndex);
        logger.info("Selected activity: {}", selectedActivity);
        return selectedActivity;
    }
}
