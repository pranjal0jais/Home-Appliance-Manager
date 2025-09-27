package com.pranjal.dtos;

import com.pranjal.entity.embeddable.ProductDetail;
import com.pranjal.entity.embeddable.VendorDetail;
import com.pranjal.entity.embeddable.WarrantyDetail;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String userId;
    private ProductDetail productDetail;
    private VendorDetail vendorDetail;
    private WarrantyDetail warrantyDetail;
    private InvoiceRequest invoiceRequest;
    private MultipartFile invoiceImage;
}
