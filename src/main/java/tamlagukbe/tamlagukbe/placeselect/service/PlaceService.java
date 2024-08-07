package tamlagukbe.tamlagukbe.placeselect.service;

import tamlagukbe.tamlagukbe.placeselect.entity.Place;
import tamlagukbe.tamlagukbe.placeselect.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Random;

@Service
public class PlaceService {

    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    @Autowired
    private PlaceRepository placeRepository;

    public Place getRandomPlaceByExistsFree(String existsFree) {
        // URL 디코딩 수행
        String decodedExistsFree;
        try {
            decodedExistsFree = URLDecoder.decode(existsFree, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding existsFree: {}", existsFree, e);
            return null;
        }
        logger.info("Encoded existsFree: {}", existsFree);
        logger.info("Decoded existsFree: {}", decodedExistsFree);

        // 디코딩된 값을 사용하여 장소 검색
        List<Place> places = placeRepository.findByExistsFree(decodedExistsFree);
        if (places.isEmpty()) {
            logger.warn("No place found for existsFree: {}", decodedExistsFree);
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(places.size());
        Place selectedPlace = places.get(randomIndex);
        logger.info("Selected place: {}", selectedPlace);
        return selectedPlace;
    }
}
