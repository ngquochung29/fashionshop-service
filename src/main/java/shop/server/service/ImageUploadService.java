package shop.server.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
public interface ImageUploadService {

    String uploadImage(MultipartFile file) throws IOException;
}
