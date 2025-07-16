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
@Table(name = "PRODUCT")
public class ProductEntity {
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
    //SP cha chứa các thông tin tong quat de show ra cho nguoi dung xem,
    //SP con chứa các thông tin cụ thể để bán
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE}, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<ProductDetailEntity> children;
}
