package shop.server.rest.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.enums.MasterDataType;
import shop.server.service.MasterDataService;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
@RequestMapping("/api/external/master-data")
@Slf4j
@RestController
public class MasterDataExtApi {
    private MasterDataService masterData;

    @GetMapping("/category")
    private ApiBaseResp getCategory() {
        return ApiBaseResp.builder().data(masterData.getData(MasterDataType.CATEGORY)).build();
    }

    @GetMapping("/brand")
    private ApiBaseResp getBrand() {
        return ApiBaseResp.builder().data(masterData.getData(MasterDataType.BRAND)).build();
    }
}
