package shop.server.rest.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.server.model.dto.ApiBaseResp;
import shop.server.service.ImageUploadService;

import java.io.IOException;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
@RestController
@RequestMapping("/api/external/image")
@Slf4j
public class ImageApi {

    private final ImageUploadService imageUploadService;

    public ImageApi(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload")
    public ApiBaseResp uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = imageUploadService.uploadImage(file);
            return new ApiBaseResp(imageUrl);
        } catch (IOException e) {
            return new ApiBaseResp(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

}
