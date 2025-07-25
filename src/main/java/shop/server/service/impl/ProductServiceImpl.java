package shop.server.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.server.exception.FashionException;
import shop.server.model.dto.PageDto;
import shop.server.model.dto.ProductDetailDto;
import shop.server.model.dto.ProductDto;
import shop.server.model.dto.ProductQuery;
import shop.server.model.entity.ProductDetailEntity;
import shop.server.model.entity.ProductEntity;
import shop.server.repo.ProductDetailRepo;
import shop.server.repo.ProductRepo;
import shop.server.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;
import shop.server.util.CommonUtil;

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
        ProductEntity productEntity = productRepo.findByCodeAndActiveIsTrue(code)
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_GATEWAY, "Product code not exist"));
        ProductDto productDto = mapperToProductDto(productEntity);
        List<ProductDetailEntity> productDetailEntities = productDetailRepo.findByParentCode(productEntity.getCode());
        if (productDetailEntities != null && !productDetailEntities.isEmpty()) {
            productDto.setProductDetails(productDetailEntities.stream().map(this::mapToProductDetailDto).collect(Collectors.toList()));
        }
        return productDto;
    }

    @Override
    public PageDto getAllProducts(ProductQuery query) {
        Sort sort = Sort.by(
                query.getSortDir().equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                query.getSortBy()
        );
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);
        Page<ProductEntity> productEntityPage = productRepo.findAll(filter(query), pageable);
        List<ProductDto> productDtos = productEntityPage.getContent().stream().map(this::mapperToProductDto).toList();
        return PageDto.builder().totalPages(productEntityPage.getTotalPages())
                .pageCurrent(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalRecords(productEntityPage.getTotalElements())
                .data(productDtos)
        .build();
    }

    @Override
    public String createProduct(ProductDto productDto) {
        ProductEntity productEntity = productRepo.findByCodeAndActiveIsTrue(productDto.getCode()).orElse(new ProductEntity());
        mapperToProductEntity(productDto,productEntity);
        String code = "SP"+CommonUtil.random();
        while (productRepo.existsByCode(code)) {
            code = "SP"+CommonUtil.random();
        }
        productEntity.setCode(code);
        productEntity.setActive(true);
        productRepo.save(productEntity);
        return code;
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        ProductEntity productEntity = productRepo.findByCodeAndActiveIsTrue(productDto.getCode()).orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        mapperToProductEntity(productDto,productEntity);
        productRepo.save(productEntity);
    }


    @Override
    public void deleteProduct(String code) {
        ProductEntity productEntity = productRepo.findByCodeAndActiveIsTrue(code).orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        productEntity.setActive(false);
        productRepo.save(productEntity);
    }

    @Override
    public void createProductDetail(ProductDetailDto detailDto) {
        ProductEntity productEntity = productRepo.findByCodeAndActiveIsTrue(detailDto.getCode())
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        productDetailEntity.setCode(UUID.randomUUID().toString());
        productDetailEntity.setParentCode(detailDto.getParentCode());
        productDetailEntity.setSize(detailDto.getSize());
        productDetailEntity.setColor(detailDto.getColor());
        productDetailEntity.setPrice(detailDto.getPrice());
        productDetailEntity.setQuantity(detailDto.getQuantity());
        productDetailEntity.setImageUrl(detailDto.getImageUrl());
        productDetailEntity.setParent(productEntity);
        productDetailEntity.setActive(true);
        productDetailRepo.save(productDetailEntity);
    }

    @Override
    public void updateProductDetail(ProductDetailDto detailDto) {
        ProductDetailEntity productDetailEntity = productDetailRepo.findByCode(detailDto.getCode())
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        productDetailEntity.setSize(detailDto.getSize());
        productDetailEntity.setColor(detailDto.getColor());
        productDetailEntity.setPrice(detailDto.getPrice());
        productDetailEntity.setQuantity(detailDto.getQuantity());
        productDetailEntity.setImageUrl(detailDto.getImageUrl());
        productDetailRepo.save(productDetailEntity);
    }

    @Override
    public void delete(String code) {
        ProductDetailEntity productDetailEntity = productDetailRepo.findByCode(code)
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        productDetailEntity.setActive(false);
        productDetailRepo.save(productDetailEntity);
    }

    @Override
    public long countProductsDetail(ProductDetailDto detailDto) {
        List<ProductDetailEntity> productDetailEntities = productDetailRepo.findByParentCode(detailDto.getParentCode());

        return productDetailEntities.stream()
                .filter(pd -> isValidSize(detailDto, pd) && isValidColor(detailDto, pd))
                .count();
    }

    // Kiểm tra nếu Size hợp lệ
    private boolean isValidSize(ProductDetailDto detailDto, ProductDetailEntity pd) {
        return detailDto.getSize() == null || detailDto.getSize().equals(pd.getSize());
    }

    // Kiểm tra nếu Color hợp lệ
    private boolean isValidColor(ProductDetailDto detailDto, ProductDetailEntity pd) {
        return detailDto.getColor() == null || detailDto.getColor().equals(pd.getColor());
    }


    public static Specification<ProductEntity> filter(ProductQuery productQuery) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!CommonUtil.isEmpty(productQuery.getQuery())) {
                String pattern = "%" + productQuery.getQuery().toLowerCase() + "%";

                Predicate codeLike = cb.like(cb.lower(root.get("code")), pattern);
                Predicate nameLike = cb.like(cb.lower(root.get("name")), pattern);
                Predicate descLike = cb.like(cb.lower(root.get("description")), pattern);
                Predicate cateLike = cb.like(cb.lower(root.get("category")), pattern);
                Predicate brandLike = cb.like(cb.lower(root.get("brand")), pattern);
                Predicate modeLike = cb.like(cb.lower(root.get("mode")), pattern);

                predicates.add(cb.or(codeLike, nameLike, descLike,cateLike,brandLike,modeLike));
            }
            if (!CommonUtil.isEmpty(productQuery.getCategory()) ) {
                predicates.add(cb.like(root.get("category"), "%" + productQuery.getCategory() + "%"));
            }
            if (!CommonUtil.isEmpty(productQuery.getBrand()) ) {
                predicates.add(cb.equal(root.get("brand"), productQuery.getBrand()));
            }
            predicates.add(cb.equal(root.get("active"), true));
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
        productDto.setMode(productEntity.getMode());
        productDto.setAvtUrl(productEntity.getAvtUrl());
        return productDto;
    }

    private void mapperToProductEntity(ProductDto productDto,ProductEntity productEntity) {
        productEntity.setCode(productDto.getCode());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setCategory(productDto.getCategory());
        productEntity.setBrand(productDto.getBrand());
        productEntity.setMode(productDto.getMode());
        productEntity.setCreatedAt(Optional.ofNullable(productEntity.getCreatedAt()).orElse(new Date()));
        productEntity.setUpdatedAt(new Date());
    }

    private ProductDetailDto mapToProductDetailDto(ProductDetailEntity productDetailEntity) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setCode(productDetailEntity.getCode());
        productDetailDto.setParentCode(productDetailEntity.getParentCode());
        productDetailDto.setSize(productDetailEntity.getSize());
        productDetailDto.setColor(productDetailEntity.getColor());
        productDetailDto.setPrice(productDetailEntity.getPrice());
        productDetailDto.setImageUrl(productDetailEntity.getImageUrl());
        return productDetailDto;
    }

    private ProductDetailEntity mapToProductDetailEntity(ProductDetailDto productDetailDto,ProductEntity productEntity) {
        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        productDetailEntity.setCode(productDetailDto.getCode());
        productDetailEntity.setParentCode(productDetailDto.getParentCode());
        productDetailEntity.setSize(productDetailDto.getSize());
        productDetailEntity.setColor(productDetailDto.getColor());
        productDetailEntity.setPrice(productDetailDto.getPrice());
        productDetailEntity.setQuantity(productDetailDto.getQuantity());
        productDetailEntity.setParent(productEntity);
        return productDetailEntity;
    }
}
