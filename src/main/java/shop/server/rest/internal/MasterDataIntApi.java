package shop.server.rest.internal;

import jakarta.validation.Valid;
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
@RequestMapping("/api/internal/master-data")
@Slf4j
@RestController
public class MasterDataIntApi {
    private MasterDataService masterData;

    @PostMapping("/category")
    private ApiBaseResp updateCategory(@Valid @RequestBody List<CategoryDto> categories) {
        masterData.save(categories, MasterDataType.CATEGORY);
        return new ApiBaseResp();
    }

    @PostMapping("/brand")
    private ApiBaseResp updateBrand(@Valid @RequestBody List<BrandDto> brands) {
        masterData.save(brands,MasterDataType.BRAND);
        return new ApiBaseResp();
    }
}
