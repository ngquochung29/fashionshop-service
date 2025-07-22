package shop.server.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BrandDto {
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
