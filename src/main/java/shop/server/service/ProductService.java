package shop.server.service;

import shop.server.model.dto.ProductDto;


/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025, 
 **/
public interface ProductService {
    ProductDto getProductDtoByCode(String code);
//    Page<ProductDto> getAllProducts();
    void saveProduct(ProductDto productDto);
    void deleteProduct(String code);
    void updateQuantity(Long quantity);
}
