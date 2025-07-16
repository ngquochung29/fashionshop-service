package shop.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CartItemDTO {
    @JsonProperty("product_detail_id")
    private Long productDetailId;
    @JsonProperty("quantity")
    private Integer quantity;
}
