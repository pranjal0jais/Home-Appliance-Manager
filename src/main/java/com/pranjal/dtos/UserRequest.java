package com.pranjal.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String password;

    private String fullName;

    private String email;

    private String mobileNo;
}
