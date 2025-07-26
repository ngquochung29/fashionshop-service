package shop.server.rest.external;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.dto.BrandDto;
import shop.server.model.dto.CategoryDto;
import shop.server.model.enums.MasterDataType;
import shop.server.service.MasterDataService;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
@RequestMapping("/api/external/master-data")
@Slf4j
@RestController
@AllArgsConstructor
public class MasterDataExtApi {
    private final MasterDataService masterData;

    @GetMapping("/category")
    private ApiBaseResp getCategory() {
        return ApiBaseResp.builder().data(masterData.getData(MasterDataType.CATEGORY)).build();
    }

    @GetMapping("/brand")
    private ApiBaseResp getBrand() {
        return ApiBaseResp.builder().data(masterData.getData(MasterDataType.BRAND)).build();
    }

    @PostMapping("/category")
    private ApiBaseResp updateCategory( @RequestBody List<CategoryDto> categories) {
        masterData.save(categories, MasterDataType.CATEGORY);
        return new ApiBaseResp();
    }

    @PostMapping("/brand")
    private ApiBaseResp updateBrand( @RequestBody List<BrandDto> brands) {
        masterData.save(brands,MasterDataType.BRAND);
        return new ApiBaseResp();
    }
}
