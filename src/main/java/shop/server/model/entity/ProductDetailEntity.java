package shop.server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.server.model.enums.SaleStatus;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "PRODUCT_DETAIL")
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment ID
    private long id;
    private String code;
    private String parentCode;
    private String size;
    private String color;
    private Long price;
    private Long quantity;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ProductEntity parent;
}
