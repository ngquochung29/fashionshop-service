package shop.server.service;

import shop.server.model.enums.MasterDataType;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
public interface MasterDataService {
    void save(List<?> object, MasterDataType type);
    List<Object> getData(MasterDataType type);
}
