package shop.server.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shop.server.model.entity.MasterDataEntity;
import shop.server.model.enums.MasterDataType;
import shop.server.repo.MasterDataRepo;
import shop.server.service.MasterDataService;
import shop.server.util.CommonUtil;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
@Service
@AllArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {
    private final MasterDataRepo masterDataRepo;

    @Override
    public void save(List<?> object, MasterDataType type) {
        MasterDataEntity entity = masterDataRepo.findByType(type.name()).orElse(new MasterDataEntity());
        entity.setType(type.name());
        entity.setJsonData(CommonUtil.writeValue(object));
        masterDataRepo.save(entity);
    }

    @Override
    public List<Object> getData(MasterDataType type) {
        MasterDataEntity entity = masterDataRepo.findByType(type.name()).orElse(new MasterDataEntity());
        return CommonUtil.readList(entity.getJsonData(),Object.class);
    }
}
