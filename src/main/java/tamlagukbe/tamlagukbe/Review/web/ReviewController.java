package tamlagukbe.tamlagukbe.Review.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tamlagukbe.tamlagukbe.Review.entity.ActivityReview;
import tamlagukbe.tamlagukbe.Review.entity.FoodReview;
import tamlagukbe.tamlagukbe.Review.entity.PlaceReview;
import tamlagukbe.tamlagukbe.Review.service.ActivityReviewService;
import tamlagukbe.tamlagukbe.Review.service.FoodReviewService;
import tamlagukbe.tamlagukbe.Review.service.PlaceReviewService;

import java.io.IOException;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private FoodReviewService foodReviewService;

    @Autowired
    private ActivityReviewService activityReviewService;

    @Autowired
    private PlaceReviewService placeReviewService;

    @PostMapping("/food")
    public FoodReview transcribeFood(@RequestParam("file") MultipartFile file,
                                     @RequestParam("userId") Long userId,
                                     @RequestParam("placeId") Long placeId) throws IOException {
        return foodReviewService.transcribe(file, userId, placeId);
    }

    @PostMapping("/activity")
    public ActivityReview transcribeActivity(@RequestParam("file") MultipartFile file,
                                             @RequestParam("userId") Long userId,
                                             @RequestParam("placeId") Long placeId) throws IOException {
        return activityReviewService.transcribe(file, userId, placeId);
    }

    @PostMapping("/place")
    public PlaceReview transcribePlace(@RequestParam("file") MultipartFile file,
                                       @RequestParam("userId") Long userId,
                                       @RequestParam("placeId") Long placeId) throws IOException {
        return placeReviewService.transcribe(file, userId, placeId);
    }

    @GetMapping("/food")
    public List<FoodReview> getFoodReviews() {
        return foodReviewService.getReviews();
    }

    @GetMapping("/activity")
    public List<ActivityReview> getActivityReviews() {
        return activityReviewService.getReviews();
    }

    @GetMapping("/place")
    public List<PlaceReview> getPlaceReviews() {
        return placeReviewService.getReviews();
    }
}
