package shop.server.rest.external;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.dto.AuthRequest;
import shop.server.service.UserService;
import shop.server.webconfig.security.JwtUtil;

import java.util.UUID;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/

@RestController
@RequestMapping("/api/external/auth")
public class AuthApi {

//    private final UserService userService;


    @PostMapping("/login")
    public ApiBaseResp login(@RequestBody AuthRequest request) {
        return new ApiBaseResp(JwtUtil.generateAccessToken("ROLE_USER", "ROLE_USER,ROLE_ADMIN"));
    }

//    @PostMapping("/refresh")
//    public ApiBaseResp refreshToken(@RequestBody AuthDto request) {
//
//    }
}

