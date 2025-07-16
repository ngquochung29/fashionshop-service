package shop.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDto {
    private String assetToken;
    private String refreshToken;
}
