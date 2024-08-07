package tamlagukbe.tamlagukbe.Review.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tamlagukbe.tamlagukbe.Review.dto.FoodReviewDto;
import tamlagukbe.tamlagukbe.Review.entity.ActivityReview;
import tamlagukbe.tamlagukbe.Review.entity.FoodReview;
import tamlagukbe.tamlagukbe.Review.entity.PlaceReview;
import tamlagukbe.tamlagukbe.Review.service.ActivityReviewService;
import tamlagukbe.tamlagukbe.Review.service.FoodReviewService;
import tamlagukbe.tamlagukbe.Review.service.PlaceReviewService;

import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final FoodReviewService foodReviewService;
    private final ActivityReviewService activityReviewService;
    private final PlaceReviewService placeReviewService;

    @GetMapping("/food-reviews/{foodReviewId}")
    public ResponseEntity<FoodReviewDto> getFoodReview(@PathVariable Long foodReviewId) {
        FoodReviewDto review = foodReviewService.getReviewById(foodReviewId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/food-reviews/{foodStoreId}")
    public ResponseEntity<FoodReviewDto> transcribeFood(@PathVariable Long foodStoreId,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                        @RequestParam("userId") Long userId) throws IOException {
        FoodReviewDto review = foodReviewService.transcribe(file, userId, foodStoreId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/activity-reviews/{activityReviewId}")
    public ResponseEntity<ActivityReview> getActivityReview(@PathVariable Long activityReviewId) {
        ActivityReview review = activityReviewService.getReviewById(activityReviewId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/activity-reviews/{activityStoreId}")
    public ResponseEntity<ActivityReview> transcribeActivity(@PathVariable Long activityStoreId,
                                                             @RequestParam("file") MultipartFile file,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                             @RequestParam("userId") Long userId) throws IOException {
        ActivityReview review = activityReviewService.transcribe(file, userId, activityStoreId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/place-reviews/{placeReviewId}")
    public ResponseEntity<PlaceReview> getPlaceReview(@PathVariable Long placeReviewId) {
        PlaceReview review = placeReviewService.getReviewById(placeReviewId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/place-reviews/{placeStoreId}")
    public ResponseEntity<PlaceReview> transcribePlace(@PathVariable Long placeStoreId,
                                                       @RequestParam("file") MultipartFile file,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                       @RequestParam("userId") Long userId) throws IOException {
        PlaceReview review = placeReviewService.transcribe(file, userId, placeStoreId);
        return ResponseEntity.ok(review);
    }
}
