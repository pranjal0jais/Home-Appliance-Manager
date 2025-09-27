package com.pranjal.service.Implementation;

import com.pranjal.dtos.ProductRequest;
import com.pranjal.dtos.ProductResponse;
import com.pranjal.dtos.ProductUpdateRequest;
import com.pranjal.entity.Product;
import com.pranjal.entity.User;
import com.pranjal.entity.embeddable.InvoiceDetail;
import com.pranjal.entity.embeddable.ProductDetail;
import com.pranjal.entity.embeddable.VendorDetail;
import com.pranjal.entity.embeddable.WarrantyDetail;
import com.pranjal.repository.ProductRepository;
import com.pranjal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements com.pranjal.service.ProductService {

    private final ProductRepository productRepository;
    private final FileUploadServiceImpl fileUploadService;
    private final UserRepository userRepository;

    private Product toEntity(ProductRequest productRequest){
        VendorDetail vendorDetail = productRequest.getVendorDetail();
        WarrantyDetail warrantyDetail = productRequest.getWarrantyDetail();
        ProductDetail productDetail = productRequest.getProductDetail();
        InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                .invoiceNo(productRequest.getInvoiceRequest().getInvoiceNo())
                .totalAmount(productRequest.getInvoiceRequest().getTotalAmount())
                .paymentMethod(productRequest.getInvoiceRequest().getPaymentMethod())
                .Date(productRequest.getInvoiceRequest().getDate())
                .build();
        return Product.builder()
                .vendorDetail(vendorDetail)
                .warrantyDetail(warrantyDetail)
                .productDetail(productDetail)
                .invoiceDetail(invoiceDetail)
                .build();
    }

    private ProductResponse toResponse(Product product){
        VendorDetail vendorDetail = product.getVendorDetail();
        WarrantyDetail warrantyDetail = product.getWarrantyDetail();
        ProductDetail productDetail = product.getProductDetail();
        InvoiceDetail invoiceDetail = product.getInvoiceDetail();
        return ProductResponse.builder()
                .product_id(product.getProductId())
                .vendorDetail(vendorDetail)
                .warrantyDetail(warrantyDetail)
                .productDetail(productDetail)
                .invoiceDetail(invoiceDetail)
                .build();
    }

    @Override
    public boolean saveProduct(ProductRequest productRequest) {
        try {
            Product newProduct = new Product();
            User user = userRepository.findByUserId(productRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Map<String, String> fileReponse =
                    fileUploadService.uploadFile(productRequest.getInvoiceImage());
            String url = fileReponse.get("secure_url");
            String publicId = fileReponse.get("public_id");
            newProduct = toEntity(productRequest);

            newProduct.setProductId(UUID.randomUUID().toString());
            newProduct.setUser(user);
            newProduct.getInvoiceDetail().setImage(url);
            newProduct.getInvoiceDetail().setPublicId(publicId);
            productRepository.save(newProduct);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findByProductId(id)
                .orElseThrow(()->new RuntimeException("Product not found."));
        return toResponse(product);
    }

    @Override
    public boolean updateProduct(ProductUpdateRequest productUpdateRequest) {
        try {
            Product existingProduct = productRepository.findByProductId(productUpdateRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found."));

            if (productUpdateRequest.getProductDetail() != null) {
                existingProduct.setProductDetail(productUpdateRequest.getProductDetail());
            }
            if (productUpdateRequest.getVendorDetail() != null) {
                existingProduct.setVendorDetail(productUpdateRequest.getVendorDetail());
            }
            if (productUpdateRequest.getWarrantyDetail() != null) {
                existingProduct.setWarrantyDetail(productUpdateRequest.getWarrantyDetail());
            }

            if (productUpdateRequest.getInvoiceRequest() != null) {
                InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                        .invoiceNo(productUpdateRequest.getInvoiceRequest().getInvoiceNo())
                        .Date(productUpdateRequest.getInvoiceRequest().getDate())
                        .totalAmount(productUpdateRequest.getInvoiceRequest().getTotalAmount())
                        .paymentMethod(productUpdateRequest.getInvoiceRequest().getPaymentMethod())
                        .image(existingProduct.getInvoiceDetail().getImage())
                        .build();
                existingProduct.setInvoiceDetail(invoiceDetail);
            }

            Product savedProduct = productRepository.save(existingProduct);

            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return false;
    }


    @Override
    public List<ProductResponse> getAllProductsByUserId(String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("User not found."));
        return productRepository.findProductByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public boolean deleteProduct(String id) {
        try{
            String publicId = productRepository.findByProductId(id)
                    .orElseThrow(()->new RuntimeException("Product not found.")).getInvoiceDetail().getPublicId();
            fileUploadService.delete(publicId);
            productRepository.deleteByProductId(id);
            return true;
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return false;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkWarrantyExpiry() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            boolean expired = !LocalDate.now().isBefore(product.getWarrantyDetail().getWarrantyExpiry());
            product.getWarrantyDetail().setIsWarrantyExpired(expired);
            productRepository.save(product);
        }
    }

}
