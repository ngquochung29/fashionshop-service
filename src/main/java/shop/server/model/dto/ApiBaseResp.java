package shop.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.sql.Timestamp;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@Data
@Builder
@AllArgsConstructor
public class ApiBaseResp {
    private HttpStatusCode status = HttpStatus.OK;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private Object data;

    public ApiBaseResp(Object data) {
        this.data = data;
    }

    public ApiBaseResp() {
    }
}
