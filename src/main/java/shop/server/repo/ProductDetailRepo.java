package shop.server.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shop.server.model.entity.ProductDetailEntity;
import shop.server.model.entity.ProductEntity;
import shop.server.model.enums.SaleStatus;

import java.util.List;
import java.util.Optional;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Repository
public interface ProductDetailRepo extends CrudRepository<ProductDetailEntity,Long> {
    List<ProductDetailEntity> findByParent(ProductEntity parent);
    List<ProductDetailEntity> findByParentCode(String parentCode);

    List<ProductDetailEntity> findByParentCodeOrderByIdDesc(String parentCode);
    Optional<ProductDetailEntity> findByCode(String code);
    void deleteByCode(String code);

    boolean existsByCode(String code);
}
