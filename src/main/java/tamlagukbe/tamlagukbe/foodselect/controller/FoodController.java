package tamlagukbe.tamlagukbe.foodselect.controller;

import tamlagukbe.tamlagukbe.foodselect.dto.FoodDto;
import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import tamlagukbe.tamlagukbe.foodselect.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    private FoodService foodService;

    @GetMapping("/random-food")
    public ResponseEntity<FoodDto> getRandomFood(@RequestParam String category) {
        Food food = foodService.getRandomFoodByCategory(category);
        if (food == null) {
            logger.warn("No food found for category: {}", category);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 상태 코드 반환
        }

        FoodDto foodDto = new FoodDto();
        foodDto.setId(food.getId());
        foodDto.setName(food.getName());
        foodDto.setCategory(food.getCategory());
        logger.info("Randomly selected food DTO: {}", foodDto);
        return new ResponseEntity<>(foodDto, HttpStatus.OK);
    }
}
