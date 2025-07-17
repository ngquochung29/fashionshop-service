package shop.server.rest.internal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.*;
import shop.server.model.dto.ApiBaseResp;
import shop.server.model.dto.ProductDto;
import shop.server.model.dto.ProductQuery;
import shop.server.service.ProductService;

import java.security.Principal;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 15/7/2025,
 **/

@RestController
@RequestMapping("/api/internal/product")
@Slf4j
public class ProductIntApi {
    private final ProductService productService;

    public ProductIntApi(ProductService productService) {
        this.productService=productService;
    }

    // lay DS sp
    @GetMapping("/list")
    public ApiBaseResp list(@RequestBody ProductQuery query, Principal principal) {
        System.out.println(principal.getName());
        return new ApiBaseResp();
    }

    //Lay SP theo ma Code
    @GetMapping("/{code}")
    public ApiBaseResp getProduct(@PathVariable String code) {
        return new ApiBaseResp(productService.getProductDtoByCode(code));
    }

    //Them SP
    @PostMapping("/create")
    public ApiBaseResp createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
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
