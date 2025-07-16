package shop.server.rest.external;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.server.exception.FashionException;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.dto.AuthDto;
import shop.server.model.dto.AuthRequest;
import shop.server.service.UserService;
import shop.server.webconfig.security.JwtUtil;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/

@RestController
@RequestMapping("/api/v1/external/auth")
public class AuthController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ApiBaseResp login(@RequestBody AuthRequest request) {
        return new ApiBaseResp(userService.checkUser(request));
    }

//    @PostMapping("/refresh")
//    public ApiBaseResp refreshToken(@RequestBody AuthDto request) {
//
//    }
}

