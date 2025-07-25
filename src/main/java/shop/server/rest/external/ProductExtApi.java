package shop.server.rest.external;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.*;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.dto.ProductDetailDto;
import shop.server.model.dto.ProductDto;
import shop.server.model.dto.ProductQuery;
import shop.server.service.ProductService;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@RestController
@RequestMapping("/api/external/product")
@Slf4j
public class ProductExtApi {
    private final ProductService productService;

    public ProductExtApi(ProductService productService) {
        this.productService=productService;
    }

    // lay DS sp
    @PostMapping("/get-list")
    public ApiBaseResp list(@RequestBody ProductQuery productQuery) {
        return new ApiBaseResp(productService.getAllProducts(productQuery));
    }

    //Lay SP theo ma Code
    @GetMapping("/get-by-code/{code}")
    public ApiBaseResp getProduct(@PathVariable String code) {
        return new ApiBaseResp(productService.getProductDtoByCode(code));
    }

    //Them SP
    @PostMapping("/create")
    public ApiBaseResp createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return new ApiBaseResp();
    }

    @PostMapping("/create/detail")
    public ApiBaseResp createProduct(@Valid @RequestBody ProductDetailDto productDetailDto) {
        productService.createProductDetail(productDetailDto);
        return new ApiBaseResp();
    }

    @PutMapping("/update/detail")
    public ApiBaseResp updateProduct(@RequestBody ProductDetailDto productDetailDto) {
        productService.updateProductDetail(productDetailDto);
        return new ApiBaseResp();
    }

    // sua SP
    @PutMapping("/update")
    public ApiBaseResp updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProduct(productDto);
        return new ApiBaseResp();
    }

    // xoa SP
    @DeleteMapping("/{code}")
    public ApiBaseResp deleteProduct(@PathVariable String code){
        productService.deleteProduct(code);
        return new ApiBaseResp();
    }

}
