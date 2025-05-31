package com.estuamante.shogun.controllers;

import ch.qos.logback.core.util.StringUtil;
import com.estuamante.shogun.dtos.ProductDto;
import com.estuamante.shogun.entities.Product;
import com.estuamante.shogun.services.ProductService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false) UUID categoryTypeId,
            @RequestParam(required = false) String slug,
            HttpServletResponse response
    ) {
        List<ProductDto> productDtos = new ArrayList<>();
        if (StringUtils.isNotBlank(slug)) {
            ProductDto productDto = productService.getProductBySlug(slug);
            productDtos.add(productDto);
        } else {
            productDtos = productService.getAllProduct(categoryId, categoryTypeId);
        }
        response.setHeader("Content-Range", String.valueOf(productDtos.size()));
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "id") UUID id) {
        ProductDto productDto = productService.getProductDtoById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product createdProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable(name = "id") UUID id,
            @RequestBody ProductDto productDto
    ) {
        Product updatedProduct = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
