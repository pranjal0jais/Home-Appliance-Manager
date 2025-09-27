package com.pranjal.entity;

import com.pranjal.entity.embeddable.InvoiceDetail;
import com.pranjal.entity.embeddable.ProductDetail;
import com.pranjal.entity.embeddable.VendorDetail;
import com.pranjal.entity.embeddable.WarrantyDetail;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String productId;

    @Embedded
    private ProductDetail productDetail;

    @Embedded
    private VendorDetail vendorDetail;

    @Embedded
    private WarrantyDetail warrantyDetail;

    @Embedded
    private InvoiceDetail invoiceDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
