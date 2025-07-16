package shop.server.service;

import org.apache.catalina.User;
import shop.server.model.dto.AuthDto;
import shop.server.model.dto.AuthRequest;
import shop.server.model.dto.UserDto;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
public interface UserService {
    void addUser(UserDto user);
    void updateUser(UserDto user);
    void deleteUser(long id);
    User getUser(String username);
    List<User> getUsers();
    AuthDto checkUser(AuthRequest authRequest);
}
