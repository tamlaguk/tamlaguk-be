package tamlagukbe.tamlagukbe.placeselect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceDto {
    private Long id;
    private String placeStoreName;
    private String category;
}
