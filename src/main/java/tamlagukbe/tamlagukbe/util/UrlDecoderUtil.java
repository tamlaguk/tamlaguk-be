package tamlagukbe.tamlagukbe.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecoderUtil {

    private static final Logger logger = LoggerFactory.getLogger(UrlDecoderUtil.class);

    public static String decode(String encodedString) {
        try {
            String decodedString = URLDecoder.decode(encodedString, "UTF-8");
            logger.info("Encoded string: {}", encodedString);
            logger.info("Decoded string: {}", decodedString);
            return decodedString;
        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding string: {}", encodedString, e);
            return null;
        }
    }
}
