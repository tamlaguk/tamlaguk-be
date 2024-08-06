package tamlagukbe.tamlagukbe.placeselect.controller;

import tamlagukbe.tamlagukbe.placeselect.dto.PlaceDto;
import tamlagukbe.tamlagukbe.placeselect.entity.Place;
import tamlagukbe.tamlagukbe.placeselect.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/random-place")
    public PlaceDto getRandomPlace(@RequestParam String category) {
        try {
            // URL 인코딩된 한글을 디코딩
            String decodedCategory = URLDecoder.decode(category, "UTF-8");
            System.out.println("Decoded category: " + decodedCategory); // 디코딩된 카테고리 로그 출력

            // 디코딩된 카테고리를 사용하여 Place 객체를 가져옴
            Place place = placeService.getRandomPlaceByCategory(decodedCategory);
            if (place == null) {
                System.out.println("No place found for category: " + decodedCategory);
                return null; // 또는 적절히 처리
            }

            // Place 객체를 PlaceDto로 변환하여 반환
            PlaceDto placeDto = new PlaceDto();
            placeDto.setId(place.getId());
            placeDto.setName(place.getName());
            placeDto.setCategory(place.getCategory());
            System.out.println("Randomly selected place DTO: " + placeDto); // 로그 출력
            return placeDto;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // 또는 적절히 예외 처리
        }
    }
}
