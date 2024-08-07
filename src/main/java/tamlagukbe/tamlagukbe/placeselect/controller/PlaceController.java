package tamlagukbe.tamlagukbe.placeselect.controller;

import tamlagukbe.tamlagukbe.placeselect.dto.PlaceDto;
import tamlagukbe.tamlagukbe.placeselect.entity.Place;
import tamlagukbe.tamlagukbe.placeselect.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class PlaceController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    @Autowired
    private PlaceService placeService;

    @GetMapping("/random-place")
    public ResponseEntity<PlaceDto> getRandomPlace(@RequestParam String existsFree) {
        Place place = placeService.getRandomPlaceByExistsFree(existsFree);
        if (place == null) {
            logger.warn("No place found for existsFree: {}", existsFree);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 상태 코드 반환
        }

        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(place.getId());
        placeDto.setName(place.getName());
        placeDto.setExistsFree(place.getExistsFree());
        logger.info("Randomly selected place DTO: {}", placeDto);
        return new ResponseEntity<>(placeDto, HttpStatus.OK);
    }
}
