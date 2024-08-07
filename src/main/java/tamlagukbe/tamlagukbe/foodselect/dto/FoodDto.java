package tamlagukbe.tamlagukbe.foodselect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto {
    private Long id;
    private String foodStoreName;
    private String category;


}
