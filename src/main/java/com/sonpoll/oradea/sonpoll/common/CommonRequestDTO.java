package com.sonpoll.oradea.sonpoll.common;

import lombok.Getter;

@Getter
public class CommonRequestDTO<T> {
    private T payload;
}
