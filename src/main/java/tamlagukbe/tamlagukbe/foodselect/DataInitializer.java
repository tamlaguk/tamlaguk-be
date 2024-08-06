package tamlagukbe.tamlagukbe.foodselect;

import tamlagukbe.tamlagukbe.foodselect.entity.Food;
import tamlagukbe.tamlagukbe.foodselect.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void run(String... args) throws Exception {
        // 임시 데이터 추가
        foodRepository.save(new Food(null, "Sushi", "Japanese"));
        foodRepository.save(new Food(null, "Ramen", "Japanese"));
        foodRepository.save(new Food(null, "Tempura", "Japanese"));

        System.out.println("Sample data added.");
    }
}
