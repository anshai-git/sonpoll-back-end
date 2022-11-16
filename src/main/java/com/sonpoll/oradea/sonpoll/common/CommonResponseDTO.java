package com.sonpoll.oradea.sonpoll.common;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommonResponseDTO<T> {
    private T response;
    private String error;

    private CommonResponseDTO(final T response, final String error) {
        this.response = response;
        this.error = error;
    }

    public static <T> CommonResponseDTO createSuccesResponse(final T responseData) {
        return new CommonResponseDTO(responseData, null);
    }

    public static CommonResponseDTO createFailResponse(final String error) {
        return new CommonResponseDTO(null, error);
    }
}
