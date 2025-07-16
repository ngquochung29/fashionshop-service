//package shop.server.rest.external;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import shop.server.model.dto.ApiBaseResp;
//import shop.server.model.dto.AuthRequest;
//import shop.server.service.UserService;
//import shop.server.webconfig.security.JwtUtil;
//
///**
// * @author : Nguyen Quoc Hung
// * @mailto : hungnqdatn04@gmail.com
// * @created : 16/7/2025,
// **/
//
//@RestController
//@RequestMapping("/api/v1/external/auth")
//public class AuthApi {
//
//    private final UserService userService;
//
//    private final JwtUtil jwtUtil;
//
//    public AuthApi(UserService userService, JwtUtil jwtUtil) {
//        this.userService = userService;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @PostMapping("/login")
//    public ApiBaseResp login(@RequestBody AuthRequest request) {
//        return new ApiBaseResp(userService.checkUser(request));
//    }
//
////    @PostMapping("/refresh")
////    public ApiBaseResp refreshToken(@RequestBody AuthDto request) {
////
////    }
//}
//
