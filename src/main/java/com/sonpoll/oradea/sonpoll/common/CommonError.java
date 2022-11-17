package com.sonpoll.oradea.sonpoll.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonError {
    private String code;
    private String message;

    public CommonError(String message) {
        this.message = message;
    }
}
