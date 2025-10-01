package com.pranjal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pranjal.dtos.ProductRequest;
import com.pranjal.dtos.ProductResponse;
import com.pranjal.dtos.ProductUpdateRequest;
import com.pranjal.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(
            @RequestPart("request") String stringRequest,
            @RequestPart("invoiceImage") MultipartFile invoiceImage) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ProductRequest request = objectMapper.readValue(stringRequest, ProductRequest.class);
        request.setInvoiceImage(invoiceImage);

        boolean isSaved = productService.saveProduct(request);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Product Saved");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product cannot be saved");
    }

    @GetMapping("/users")
    public ResponseEntity<List<ProductResponse>> getAllProductsByUserId(
            @RequestParam(name = "userId") String userId) {
        List<ProductResponse> list = productService.getAllProductsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getProductByProductId(
            @RequestParam(name = "productId") String productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(
            @RequestParam(name = "productId") String productId) {
        log.info("Deleting product with id: {}", productId);

        boolean deleted = productService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(
            @RequestBody ProductUpdateRequest productUpdateRequest) {

        boolean isUpdated = productService.updateProduct(productUpdateRequest);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Product Updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product not Updated");
    }

    @GetMapping("/share")
    public ResponseEntity<ProductResponse> shareProduct(
            @RequestParam(name = "productId") String productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }
}
