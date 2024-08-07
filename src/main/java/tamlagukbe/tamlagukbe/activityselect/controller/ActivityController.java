package tamlagukbe.tamlagukbe.activityselect.controller;

import tamlagukbe.tamlagukbe.activityselect.dto.ActivityDto;
import tamlagukbe.tamlagukbe.activityselect.entity.Activity;
import tamlagukbe.tamlagukbe.activityselect.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @GetMapping("/random-activity")
    public ResponseEntity<ActivityDto> getRandomActivity(@RequestParam String category) {
        Activity activity = activityService.getRandomActivityByCategory(category);
        if (activity == null) {
            logger.warn("No activity found for category: {}", category);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 상태 코드 반환
        }

        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(activity.getId());
        activityDto.setName(activity.getName());
        activityDto.setCategory(activity.getCategory());
        logger.info("Randomly selected activity DTO: {}", activityDto);
        return new ResponseEntity<>(activityDto, HttpStatus.OK);
    }
}
