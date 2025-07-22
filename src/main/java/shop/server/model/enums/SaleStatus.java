package shop.server.model.enums;

import lombok.Getter;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 18/7/2025,
 **/
@Getter
public enum SaleStatus {
    READY("Con hang"),
    PICKED("Da them vao gio hang khac"),
    SOLD("Da ban");
    private final String description;
    SaleStatus(String description) {
        this.description = description;
    }
}
