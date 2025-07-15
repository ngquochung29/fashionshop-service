package shop.server.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

public class FashionException extends RuntimeException {
    HttpStatus status;
    public FashionException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
