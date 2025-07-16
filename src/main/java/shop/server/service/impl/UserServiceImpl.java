package shop.server.service.impl;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import shop.server.model.dto.AuthDto;
import shop.server.model.dto.AuthRequest;
import shop.server.model.dto.UserDto;
import shop.server.service.UserService;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    //todo
    @Override
    public AuthDto checkUser(AuthRequest authRequest) {

        return new AuthDto();
    }
}
