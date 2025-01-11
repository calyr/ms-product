package com.example.product.controller;

import com.example.product.api.ProductApi;
import com.example.product.model.ProductDto;
import com.example.product.model.UpdateProductDto;
import com.example.product.service.AllProductService;
import com.example.product.service.CreateProductService;
import com.example.product.service.GetProductService;
import com.example.product.service.UpdateProductService;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ProductController implements ProductApi {

    @Autowired
    private Tracer tracer;

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
        // Crear un Span para la solicitud HTTP
        Span span = tracer.spanBuilder("http-request").startSpan();
        ResponseEntity<List<ProductDto>> execute = this.allProductService.execute(null);
        try {
            // Vincular el Span al contexto
            span.setAttribute("http.method", "GETALL");
            span.setAttribute("http.response", execute.getBody().toString());

        } finally {
            // Completar el Span después de la respuesta
            span.setAttribute("http.status_code", execute.getStatusCode().value());
            span.end();
        }

        return execute;
    }
}
