package com.pranjal.dtos;

import com.pranjal.entity.embeddable.PaymentMethod;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequest {
    private String invoiceNo;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
    private LocalDate Date;
}
