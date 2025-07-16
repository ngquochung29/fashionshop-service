package shop.server.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.server.exception.FashionException;
import shop.server.mapper.ProductMapper;
import shop.server.model.dto.ProductDto;
import shop.server.model.dto.ProductQuery;
import shop.server.model.entity.ProductEntity;
import shop.server.repo.ProductRepo;
import shop.server.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper mapper) {
        this.productRepo = productRepo;
        this.mapper = mapper;
    }


    @Override
    public ProductDto getProductDtoByCode(String code) {
        ProductEntity productEntity = productRepo.findByCode(code)
                .orElseThrow(() -> new FashionException(HttpStatus.BAD_GATEWAY, "Product code not exist"));
        return mapper.toProductDto(productEntity);
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
        return productEntityPage.map(mapper::toProductDto);
    }

    @Override
    public void createProduct(ProductDto productDto) {
        if (productRepo.existsByCode(productDto.getCode())) {
            throw new FashionException(HttpStatus.CONFLICT, "Product code already exist");
        }
        saveProduct(productDto);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        saveProduct(productDto);
    }

    public void saveProduct(ProductDto productDto) {
        ProductEntity productEntity = mapper.toProductEntity(productDto);
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
}
