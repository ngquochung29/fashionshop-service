package shop.server.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shop.server.model.entity.MasterDataEntity;
import shop.server.model.enums.MasterDataType;

import java.util.Optional;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
@Repository
public interface MasterDataRepo extends CrudRepository<MasterDataEntity,Integer> {
    Optional<MasterDataEntity> findByType(MasterDataType type);
}
