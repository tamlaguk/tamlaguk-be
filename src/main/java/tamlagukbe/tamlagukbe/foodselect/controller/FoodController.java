package tamlagukbe.tamlagukbe.foodselect.controller;

import tamlagukbe.tamlagukbe.foodselect.dto.FoodDto;
import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import tamlagukbe.tamlagukbe.foodselect.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/random-food")
    public FoodDto getRandomFood(@RequestParam String category) {
        try {
            // URL 인코딩된 한글을 디코딩
            String decodedCategory = URLDecoder.decode(category, "UTF-8");
            System.out.println("Decoded category: " + decodedCategory); // 디코딩된 카테고리 로그 출력

            // 디코딩된 카테고리를 사용하여 Food 객체를 가져옴
            Food food = foodService.getRandomFoodByCategory(decodedCategory);
            if (food == null) {
                System.out.println("No food found for category: " + decodedCategory);
                return null; // 또는 적절히 처리
            }

            // Food 객체를 FoodDto로 변환하여 반환
            FoodDto foodDto = new FoodDto();
            foodDto.setId(food.getId());
            foodDto.setName(food.getName());
            foodDto.setCategory(food.getCategory());
            System.out.println("Randomly selected food DTO: " + foodDto); // 로그 출력
            return foodDto;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // 또는 적절히 예외 처리
        }
    }
}
