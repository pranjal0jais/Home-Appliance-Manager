package com.pranjal.service;

import com.pranjal.dtos.ProductRequest;
import com.pranjal.dtos.ProductResponse;
import com.pranjal.dtos.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    boolean saveProduct(ProductRequest productRequest);

    ProductResponse getProductById(String id);

    boolean updateProduct(ProductUpdateRequest productUpdateRequest);

    List<ProductResponse> getAllProductsByUserId(String userId);

    boolean deleteProduct(String id);
}
