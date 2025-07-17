package shop.server.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.server.exception.FashionException;
import shop.server.model.dto.ProductDto;
import shop.server.model.dto.ProductQuery;
import shop.server.model.entity.ProductDetailEntity;
import shop.server.model.entity.ProductEntity;
import shop.server.repo.ProductRepo;
import shop.server.service.ProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;
import shop.server.util.CommonUtil;
import shop.server.webconfig.security.JwtUtil;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public ProductDto getProductDtoByCode(String code) {
        ProductEntity productEntity = productRepo.findByCode(code)
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_GATEWAY, "Product code not exist"));
        return mapperToProductDto(productEntity);
    }

    @Override
    public Page<ProductDto> getAllProducts(ProductQuery query) {
        Sort sort = Sort.by(
                query.getSortDir().equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                query.getSortBy()
        );
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);
        Page<ProductEntity> productEntityPage = productRepo.findAll(filter(query), pageable);
        return productEntityPage.map(this::mapperToProductDto);
    }

    @Override
    public void createProduct(ProductDto productDto) {
        if (productRepo.existsByCode(productDto.getCode())) {
            throw new FashionException(HttpStatus.CONFLICT, "Product code already exist");
        }
        ProductEntity productEntity = productRepo.findByCode(productDto.getCode()).orElse(new ProductEntity());
        productEntity =mapperToProductEntity(productDto,productEntity);
        productRepo.save(productEntity);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        ProductEntity productEntity = productRepo.findByCode(productDto.getCode()).orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        productEntity =mapperToProductEntity(productDto,productEntity);
        productRepo.save(productEntity);
    }


    @Override
    public void deleteProduct(String code) {
        productRepo.deleteByCode(code);
    }

    @Override
    public void updateQuantity(Long quantity) {

    }

    public static Specification<ProductEntity> filter(ProductQuery productQuery) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (productQuery.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), productQuery.getCode()));
            }
            if (productQuery.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + productQuery.getName() + "%"));
            }
            if (productQuery.getDescription() != null) {
                predicates.add(cb.like(root.get("description"), "%" + productQuery.getDescription() + "%"));
            }
            if (productQuery.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), productQuery.getCategory()));
            }
            if (productQuery.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), productQuery.getBrand()));
            }
            if (productQuery.getModel() != null) {
                predicates.add(cb.equal(root.get("model"), productQuery.getModel()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private ProductDto mapperToProductDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setCode(productEntity.getCode());
        productDto.setName(productEntity.getName());
        productDto.setDescription(productEntity.getDescription());
        productDto.setCategory(productEntity.getCategory());
        productDto.setBrand(productEntity.getBrand());
        productDto.setModel(productEntity.getModel());
        if (!CommonUtil.isEmpty(productEntity.getChildren())){
            productDto.setProductDetails(productEntity.getChildren().stream().map(
                    this::mapToProductDetailDto
            ).collect(Collectors.toList()));
        }
        return productDto;
    }

    private ProductEntity mapperToProductEntity(ProductDto productDto,ProductEntity productEntity) {
        productEntity.setCode(productDto.getCode());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setCategory(productDto.getCategory());
        productEntity.setBrand(productDto.getBrand());
        productEntity.setModel(productDto.getModel());
        productEntity.setUserCreate(Optional.ofNullable(productEntity.getUserCreate()).orElse(JwtUtil.getUsername()));
        productEntity.setUserUpdate(JwtUtil.getUsername());
        productEntity.setCreateDate(Optional.ofNullable(productEntity.getCreateDate()).orElse(new Date()));
        productEntity.setUpdateDate(new Date());
        if (!CommonUtil.isEmpty(productDto.getProductDetails())){
            productEntity.setChildren(productDto.getProductDetails().stream().map(
                    this::mapToProductDetailEntity
            ).collect(Collectors.toList()));
        }
        return productEntity;
    }

    private ProductDto.ProductDetailDto mapToProductDetailDto(ProductDetailEntity productDetailEntity) {
        ProductDto.ProductDetailDto productDetailDto = new ProductDto.ProductDetailDto();
        productDetailDto.setCode(productDetailEntity.getCode());
        productDetailDto.setParentCode(productDetailEntity.getParentCode());
        productDetailDto.setSize(productDetailEntity.getSize());
        productDetailDto.setColor(productDetailEntity.getColor());
        productDetailDto.setPrice(productDetailEntity.getPrice());
        productDetailDto.setQuantity(productDetailEntity.getQuantity());
        return productDetailDto;
    }

    private ProductDetailEntity mapToProductDetailEntity(ProductDto.ProductDetailDto productDetailDto) {
        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        productDetailEntity.setCode(productDetailDto.getCode());
        productDetailEntity.setParentCode(productDetailDto.getParentCode());
        productDetailEntity.setSize(productDetailDto.getSize());
        productDetailEntity.setColor(productDetailDto.getColor());
        productDetailEntity.setPrice(productDetailDto.getPrice());
        productDetailEntity.setQuantity(productDetailDto.getQuantity());
        return productDetailEntity;
    }
}
