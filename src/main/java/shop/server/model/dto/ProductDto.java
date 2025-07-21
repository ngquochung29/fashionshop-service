package shop.server.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private long pickTotal;
    private long readyTotal;
    private long soldTotal;
    @JsonIgnore
    private String imageUrl;
    @NotNull
    private String category;
    @NotNull
    private String brand;
    @NotNull
    private String mode;
    private Set<String> imageUrlSet = new HashSet<>();
    private Set<String> color;
    private Set<String> size;

}
