package shop.server.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.server.model.enums.SaleStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 18/7/2025,
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailDto {
    @NotNull
    private String code;
    private String parentCode;
    private String size;
    private String color;
    private Long price;
    private Long quantity;
    private List<SaleStatus> saleStatusList= new ArrayList<>();
}
