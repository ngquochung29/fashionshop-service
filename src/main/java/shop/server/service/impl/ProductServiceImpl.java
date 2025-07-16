package shop.server.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.server.exception.FashionException;
import shop.server.model.dto.ProductDto;
import shop.server.model.entity.ProductEntity;
import shop.server.repo.ProductDetailRepo;
import shop.server.repo.ProductRepo;
import shop.server.service.ProductService;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductDetailRepo productDetailRepo;

    public ProductServiceImpl(ProductRepo productRepo, ProductDetailRepo productDetailRepo) {
        this.productRepo = productRepo;
        this.productDetailRepo = productDetailRepo;
    }


    @Override
    public ProductDto getProductDtoByCode(String code) {
        ProductEntity productEntity = productRepo.findByCode(code)
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_GATEWAY, "Product code not exist"));
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
