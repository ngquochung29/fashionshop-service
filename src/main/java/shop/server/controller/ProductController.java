package shop.server.controller;

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
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    // lay DS sp
    @GetMapping("/list")
    public ApiBaseResp list(@RequestParam SpringDataWebProperties.Pageable pageable, @RequestParam String search) {
        return new ApiBaseResp();
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
        return new ApiBaseResp();
    }

    // xoa SP
    @DeleteMapping("/{code}")
    public ApiBaseResp deleteProduct(@PathVariable String code){
        productService.deleteProduct(code);
        return new ApiBaseResp();
    }
}
