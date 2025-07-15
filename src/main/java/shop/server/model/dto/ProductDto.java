package shop.server.model.dto;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String code; // ma San pham, tuyet doi k leak id san pham ra client
    private String name;
    private String description;
    private Long totalQuantity;
    private String[] imageUrl;
    private String category;
    private String brand;
    private String model;
    private List<ProductDetailDto> productDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetailDto {
        private String code;
        private String parentCode;
        private String size;
        private String color;
        private String price;
        private String quantity;
    }
}
