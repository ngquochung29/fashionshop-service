package shop.server.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.server.model.dto.ProductDetailDto;
import shop.server.model.dto.ProductDto;
import shop.server.model.dto.ProductQuery;
import shop.server.model.enums.SaleStatus;


/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025, 
 **/
public interface ProductService {
    ProductDto getProductDtoByCode(String code);
    Page<ProductDto> getAllProducts(ProductQuery query);
    void createProduct(ProductDto productDto);
    void updateProduct(ProductDto productDto);
    void deleteProduct(String code);
    void createProductDetail(ProductDetailDto detailDto);
    void updateProductDetail(ProductDetailDto detailDto);
    void delete(String code);
    long countProductsDetail(ProductDetailDto detailDto);

}
