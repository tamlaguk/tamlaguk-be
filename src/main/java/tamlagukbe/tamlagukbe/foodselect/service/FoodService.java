package tamlagukbe.tamlagukbe.foodselect.service;

import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import tamlagukbe.tamlagukbe.foodselect.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    private Random random = new Random();

    // 카테고리에 따라 랜덤으로 음식점을 반환하는 메서드
    public Food getRandomFoodByCategory(String category) {
        // 주어진 카테고리에 해당하는 음식 목록을 가져옴
        List<Food> foods = foodRepository.findByCategory(category);
        if (foods.isEmpty()) {
            return null; // 또는 예외 처리
        }
        // 음식 목록에서 랜덤으로 하나를 선택
        return foods.get(random.nextInt(foods.size()));
    }
}
