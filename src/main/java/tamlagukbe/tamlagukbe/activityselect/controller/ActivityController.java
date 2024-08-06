package tamlagukbe.tamlagukbe.activityselect.controller;

import tamlagukbe.tamlagukbe.activityselect.dto.ActivityDto;
import tamlagukbe.tamlagukbe.activityselect.entity.Activity;
import tamlagukbe.tamlagukbe.activityselect.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/random-activity")
    public ActivityDto getRandomActivity(@RequestParam String category) {
        try {
            // URL 인코딩된 한글을 디코딩
            String decodedCategory = URLDecoder.decode(category, "UTF-8");
            System.out.println("Decoded category: " + decodedCategory); // 디코딩된 카테고리 로그 출력

            // 디코딩된 카테고리를 사용하여 Activity 객체를 가져옴
            Activity activity = activityService.getRandomActivityByCategory(decodedCategory);
            if (activity == null) {
                System.out.println("No activity found for category: " + decodedCategory);
                return null; // 또는 적절히 처리
            }

            // Activity 객체를 ActivityDto로 변환하여 반환
            ActivityDto activityDto = new ActivityDto();
            activityDto.setId(activity.getId());
            activityDto.setName(activity.getName());
            activityDto.setCategory(activity.getCategory());
            System.out.println("Randomly selected activity DTO: " + activityDto); // 로그 출력
            return activityDto;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // 또는 적절히 예외 처리
        }
    }
}
