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
        ProductEntity productEntity = productRepo.findByCode(code)
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_GATEWAY, "Product code not exist"));
        ProductDto productDto = mapperToProductDto(productEntity);
        List<ProductDetailEntity> productDetailEntities = productDetailRepo.findByParent(productEntity);
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
    public void createProductDetail(ProductDetailDto detailDto) {
        ProductEntity productEntity = productRepo.findByCode(detailDto.getCode())
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        productDetailEntity.setCode(detailDto.getCode());
        productDetailEntity.setParentCode(detailDto.getParentCode());
        productDetailEntity.setSize(detailDto.getSize());
        productDetailEntity.setColor(detailDto.getColor());
        productDetailEntity.setPrice(detailDto.getPrice());
        productDetailEntity.setQuantity(detailDto.getQuantity());
        productDetailEntity.setImageUrl(detailDto.getImageUrl());
        productDetailEntity.setParent(productEntity);
        productDetailRepo.save(productDetailEntity);
    }

    @Override
    public void updateProductDetail(ProductDetailDto detailDto) {
        ProductDetailEntity productDetailEntity = productDetailRepo.findByCode(detailDto.getCode())
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_REQUEST, "Product code not exist"));
        productDetailEntity.setCode(detailDto.getCode());
        productDetailEntity.setSize(detailDto.getSize());
        productDetailEntity.setColor(detailDto.getColor());
        productDetailEntity.setPrice(detailDto.getPrice());
        productDetailEntity.setQuantity(detailDto.getQuantity());
        productDetailRepo.save(productDetailEntity);
    }

    @Override
    public void delete(String code) {
        productDetailRepo.deleteByCode(code);
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
                predicates.add(cb.like(root.get("category"), "%" + productQuery.getCategory() + "%"));
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
        productDto.setMode(productEntity.getMode());
        productDto.setAvtUrl(productEntity.getAvtUrl());
        return productDto;
    }

    private ProductEntity mapperToProductEntity(ProductDto productDto,ProductEntity productEntity) {
        productEntity.setCode(productDto.getCode());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setCategory(productDto.getCategory());
        productEntity.setBrand(productDto.getBrand());
        productEntity.setMode(productDto.getMode());
        productEntity.setCreatedAt(Optional.ofNullable(productEntity.getCreatedAt()).orElse(new Date()));
        productEntity.setUpdatedAt(new Date());
        return productEntity;
    }

    private ProductDetailDto mapToProductDetailDto(ProductDetailEntity productDetailEntity) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setCode(productDetailEntity.getCode());
        productDetailDto.setParentCode(productDetailEntity.getParentCode());
        productDetailDto.setSize(productDetailEntity.getSize());
        productDetailDto.setColor(productDetailEntity.getColor());
        productDetailDto.setPrice(productDetailEntity.getPrice());
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
