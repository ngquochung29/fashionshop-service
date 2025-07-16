package shop.server.service.impl;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.server.exception.FashionException;
import shop.server.model.dto.AuthDto;
import shop.server.model.dto.AuthRequest;
import shop.server.model.dto.UserDto;
import shop.server.model.entity.UserEntity;
import shop.server.repo.UserRepo;
import shop.server.service.UserService;
import shop.server.webconfig.security.JwtUtil;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Service
public class UserServiceImpl implements UserService {
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;

    public UserServiceImpl(JwtUtil jwtUtil, UserRepo userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    public void addUser(UserDto user) {

    }

    @Override
    public void updateUser(UserDto user) {

    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public User getUser(String userName) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    //todo
    @Override
    public AuthDto checkUser(AuthRequest authRequest) {
        UserEntity userEntity = userRepo.findByUsername(authRequest.getUsername());
        if (userEntity == null || !userEntity.getPassword().equals(authRequest.getPassword())) {
            throw new FashionException(HttpStatus.UNAUTHORIZED,"username or password incorrect");
        }
        return new AuthDto(
                jwtUtil.generateAccessToken(authRequest.getUsername(), userEntity.getRoles()),
                jwtUtil.generateRefreshToken(authRequest.getUsername())
        );
    }
}
