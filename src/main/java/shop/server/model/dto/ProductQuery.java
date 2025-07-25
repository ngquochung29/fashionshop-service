package shop.server.model.dto;

import lombok.*;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQuery {
    private String query;
    private String category;
    private String brand;
    private String sortBy = "id";
    private String sortDir = "desc";
    private Integer page = 0;
    private Integer size = 99999999;
}
