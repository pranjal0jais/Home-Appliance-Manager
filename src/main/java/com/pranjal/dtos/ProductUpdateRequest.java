package com.pranjal.dtos;

import com.pranjal.entity.embeddable.ProductDetail;
import com.pranjal.entity.embeddable.VendorDetail;
import com.pranjal.entity.embeddable.WarrantyDetail;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {
    private String productId;
    private String userId;
    private ProductDetail productDetail;
    private VendorDetail vendorDetail;
    private WarrantyDetail warrantyDetail;
    private InvoiceRequest invoiceRequest;
}
