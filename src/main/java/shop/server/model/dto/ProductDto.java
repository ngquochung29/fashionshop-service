package shop.server.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String code; // ma San pham, tuyet doi k leak id san pham ra client
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Long totalQuantity;
    @JsonIgnore
    private String imageUrl;
    @NotNull
    private String category;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    private List<ProductDetailDto> productDetails;
    private List<String> imageUrlList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getImageUrl() {
        return this.imageUrlList == null ? null : String.join(",", this.imageUrlList);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImageUrlList() {
        return this.imageUrlList = this.imageUrl == null? new ArrayList<>() : List.of(this.imageUrl.split(","));
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ProductDetailDto> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetailDto> productDetails) {
        this.productDetails = productDetails;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetailDto {
        @NotNull
        private String code;
        private String parentCode;
        private String size;
        private String color;
        private String price;
        private String quantity;
    }
}
