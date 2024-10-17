package com.example.product.controller;

import com.example.product.api.ProductApi;
import com.example.product.model.ProductDto;
import com.example.product.model.UpdateProductDto;
import com.example.product.service.AllProductService;
import com.example.product.service.CreateProductService;
import com.example.product.service.GetProductService;
import com.example.product.service.UpdateProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
public class ProductController implements ProductApi {

    private final AllProductService allProductService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;
    private final GetProductService getProductService;
    ProductController(AllProductService allProductService,
                      CreateProductService createProductService,
                      UpdateProductService updateProductService,
                      GetProductService getProductService) {
        this.allProductService = allProductService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
        this.getProductService = getProductService;
    }

    @Override
    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody ProductDto productDto
    ) {
        return createProductService.execute(productDto);
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<ProductDto> obtain(@PathVariable Integer id) {
        return this.getProductService.execute(id);
    }
    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        return this.updateProductService.execute(new UpdateProductDto(id, productDto));
    }

    @GetMapping("all")
    @Override
    public ResponseEntity<List<ProductDto>> index() {
        return this.allProductService.execute(null);
    }
}
