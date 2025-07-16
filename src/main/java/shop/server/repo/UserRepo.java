package shop.server.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shop.server.model.entity.UserEntity;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
