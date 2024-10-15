package com.example.product.service;

import com.example.product.Command;
import com.example.product.IProductRepository;
import com.example.product.model.Product;
import com.example.product.model.ProductDto;
import com.example.product.model.UpdateProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UpdateProductService implements Command<UpdateProductDto, ProductDto> {

    private final IProductRepository iProductRepository;

    UpdateProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }
    @Override
    public ResponseEntity<ProductDto> execute(UpdateProductDto input) {
        Optional<Product> product = iProductRepository.findById(input.getId());

        if(product.isPresent()) {
            product.get().setDescription(input.getProductDto().getDescription());
            product.get().setName(input.getProductDto().getName());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ProductDto(iProductRepository.save(product.get())));
        }
        return  null;

    }
}
