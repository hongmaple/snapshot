package com.snapshot.dto.request;

import lombok.Data;

@Data
public class UpdatePwdReq {
    private String password;
    private String password1;
    private String password2;
}
