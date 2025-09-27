package com.pranjal.entity.embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverrides(
        {
                @AttributeOverride(name="name", column = @jakarta.persistence.Column(name=
                        "vendor_name")),
        }
)
public class VendorDetail {
    private String name;
    private String contactNo;
    private String address;
}
