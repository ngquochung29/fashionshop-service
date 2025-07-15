package shop.server.rest.external;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.*;
import shop.server.model.dto.ApiBaseResp;
import shop.server.service.ProductService;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@RestController
@RequestMapping("/api/v1/external")
@Slf4j
public class ProductExtApi {
    private final ProductService productService;

    public ProductExtApi(ProductService productService) {
        this.productService=productService;
    }

    // lay DS sp
    @GetMapping("/list")
    public ApiBaseResp list(@RequestParam SpringDataWebProperties.Pageable pageable, @RequestParam String search) {
        return new ApiBaseResp(null);
    }

    //Lay SP theo ma Code
    @GetMapping("/{code}")
    public ApiBaseResp getProduct(@PathVariable String code) {
        return new ApiBaseResp(productService.getProductDtoByCode(code));
    }

}
