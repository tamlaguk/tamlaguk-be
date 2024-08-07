package tamlagukbe.tamlagukbe.foodselect.service;

import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import tamlagukbe.tamlagukbe.foodselect.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Random;

@Service
public class FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

    @Autowired
    private FoodRepository foodRepository;

    public Food getRandomFoodByCategory(String category) {
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

        // 디코딩된 카테고리를 사용하여 음식 검색
        List<Food> foods = foodRepository.findByCategory(decodedCategory);
        if (foods.isEmpty()) {
            logger.warn("No food found for category: {}", decodedCategory);
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(foods.size());
        Food selectedFood = foods.get(randomIndex);
        logger.info("Selected food: {}", selectedFood);
        return selectedFood;
    }
}
