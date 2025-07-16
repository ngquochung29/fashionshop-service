package shop.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shop.server.model.entity.ProductEntity;

import java.util.Optional;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long>,
        JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByCode(String code);
    void deleteByCode(String code);
    boolean existsByCode(String code);
}
