package shop.server.rest.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.*;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.dto.ProductDto;
import shop.server.service.ProductService;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@RestController
@RequestMapping("/api/v1/internal")
@Slf4j
public class ProductIntApi {
    private final ProductService productService;

    public ProductIntApi(ProductService productService) {
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

    //Them, Sua SP
    @PostMapping("/")
    public ApiBaseResp saveProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
        return new ApiBaseResp(null);
    }

    // xoa SP
    @DeleteMapping("/{code}")
    public ApiBaseResp deleteProduct(@PathVariable String code){
        productService.deleteProduct(code);
        return new ApiBaseResp(null);
    }
}
