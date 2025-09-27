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
                        "product_name")),
        }
)
public class ProductDetail {
    private String name;
    private String model_no;
    private String category;
    private String description;
}
