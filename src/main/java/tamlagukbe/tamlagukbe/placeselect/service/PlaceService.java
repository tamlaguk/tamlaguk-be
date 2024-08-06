package tamlagukbe.tamlagukbe.placeselect.service;

import tamlagukbe.tamlagukbe.placeselect.entity.Place;
import tamlagukbe.tamlagukbe.placeselect.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public Place getRandomPlaceByCategory(String category) {
        List<Place> places = placeRepository.findByCategory(category);
        if (places.isEmpty()) {
            return null; // 카테고리에 해당하는 장소가 없는 경우
        }
        Random random = new Random();
        int randomIndex = random.nextInt(places.size());
        return places.get(randomIndex); // 무작위로 장소 선택
    }
}
