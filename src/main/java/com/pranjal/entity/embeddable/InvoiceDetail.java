package com.pranjal.entity.embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverrides(
        {
                @AttributeOverride(name="image", column = @jakarta.persistence.Column(name=
                        "invoice_image")),
        }
)
public class InvoiceDetail {
    private String invoiceNo;
    private Double totalAmount;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private PaymentMethod paymentMethod;
    private LocalDate Date;
    private String image;
    private String publicId;
}
