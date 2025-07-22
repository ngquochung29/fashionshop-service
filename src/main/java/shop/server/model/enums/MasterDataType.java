package shop.server.model.enums;

import lombok.Getter;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
@Getter
public enum MasterDataType {
    CATEGORY("luu the loai san pham"),
    BRAND("luu nhan hieu san pham");

    private final String desc;
    MasterDataType(String desc) {
        this.desc = desc;
    }

}
