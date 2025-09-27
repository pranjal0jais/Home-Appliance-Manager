package com.pranjal.entity.embeddable;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarrantyDetail {
    private int warranty;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyExpiry;
    private Boolean isWarrantyExpired;
}
