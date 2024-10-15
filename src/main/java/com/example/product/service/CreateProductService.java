package com.example.product.service;

import com.example.product.Command;
import com.example.product.IProductRepository;
import com.example.product.exception.ProductBadRequestException;
import com.example.product.model.Product;
import com.example.product.model.ProductDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService implements Command<ProductDto, String> {

    private final IProductRepository iProductRepository;

    public CreateProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    @Override
    public ResponseEntity<String> execute(ProductDto input) {

        Product product = input.toProduct();

        if(StringUtils.isEmpty(product.getName())) {
            throw new ProductBadRequestException("Name is required.");
        }

        int id = iProductRepository.save(product).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Product %d, created", id));
    }
}
