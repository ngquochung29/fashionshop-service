package shop.server.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CategoryDto {
    private String code;
//    @NotBlank
    private String name;
//    @NotBlank
    private String image;
//    @Valid
//    @Size(min = 1) // Optional: chỉ nếu bạn muốn có ít nhất 1 phần tử
    private List<@Valid CategoryDto> child;
}
