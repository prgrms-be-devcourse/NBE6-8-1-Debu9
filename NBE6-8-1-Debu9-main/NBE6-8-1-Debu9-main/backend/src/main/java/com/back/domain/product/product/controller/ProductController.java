package com.back.domain.product.product.controller;

import com.back.domain.product.product.dto.ProductDto;
import com.back.domain.product.product.entity.Product;
import com.back.domain.product.product.service.ProductService;
import com.back.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name =  "PostController", description = "API 상품 컨트롤러")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "다건 조회")
    public List<ProductDto> getItems() {
        List<Product> items = productService.findAll();

        return items
                .stream()
                .map(ProductDto::new) // ProductDto로 변환
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "단건 조회")
    public ProductDto getItem(@PathVariable int id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다. id = " + id));
        return new ProductDto(product);
    }

    record ProductReqBody(
            @NotBlank @Size(min = 1, max = 100)
            String name,
            @NotBlank
            String imageUrl,
            @NotBlank
            String info,
            @NotNull
            Integer price,
            @NotBlank
            String engName
    ) {}


    @PostMapping
    @Transactional
    @Operation(summary = "상품 추가")
    public RsData<ProductDto> createProduct(@Valid @RequestBody ProductReqBody reqBody) {
        Product product = productService.create(reqBody.name, reqBody.imageUrl, reqBody.info, reqBody.price, reqBody.engName);

        return new RsData<>(
                "200-1",
                "상품이 추가되었습니다.",
                new ProductDto(product)
        );
    }


    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "상품 수정")
    public RsData<Void> updateProduct(
            @PathVariable int id,
            @Valid @RequestBody ProductReqBody reqBody
    ) {
        Product product = productService.findById(id).get();
        productService.modify(product, reqBody.name, reqBody.imageUrl, reqBody.info, reqBody.price, reqBody.engName);

        return new RsData<>(
                "200-1",
                "%d번 상품이 수정되었습니다.".formatted(product.getId())
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "상품 삭제")
    public RsData<Void> delete(@PathVariable int id) {
        Product product = productService.findById(id).get();

        productService.delete(product);

        return new RsData<>(
                "200-1",
                "%d번 상품이 삭제되었습니다.".formatted(id)
        );
    }
}
