package shop.server.service.impl;

import org.springframework.stereotype.Service;
import shop.server.model.dto.ProductDto;
import shop.server.service.ProductService;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public ProductDto getProductDtoByCode(String code) {
        return null;
    }

    @Override
    public void saveProduct(ProductDto productDto) {

    }

    @Override
    public void deleteProduct(String code) {

    }

    @Override
    public void updateQuantity(Long quantity) {

    }
}
