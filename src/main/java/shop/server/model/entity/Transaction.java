package shop.server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    private long id;
    private String code;
    private String name;
    private String description;
    private Long totalQuantity;
    private String imageUrl;
    private String category;
    private String brand;
    private String model;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trans_prods", // tên bảng trung gian
            joinColumns = @JoinColumn(name = "trans_id"), // FK trỏ tới trans
            inverseJoinColumns = @JoinColumn(name = "prods_id") // FK trỏ tới product detail
    )
    private List<ProductDetailEntity> productDetails;
}
