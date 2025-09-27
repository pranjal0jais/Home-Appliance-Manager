package com.pranjal.dtos;

import com.pranjal.entity.embeddable.InvoiceDetail;
import com.pranjal.entity.embeddable.ProductDetail;
import com.pranjal.entity.embeddable.VendorDetail;
import com.pranjal.entity.embeddable.WarrantyDetail;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String product_id;
    private ProductDetail productDetail;
    private VendorDetail vendorDetail;
    private WarrantyDetail warrantyDetail;
    private InvoiceDetail invoiceDetail;
}
