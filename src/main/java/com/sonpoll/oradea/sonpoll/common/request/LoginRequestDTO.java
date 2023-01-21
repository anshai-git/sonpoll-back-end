package com.sonpoll.oradea.sonpoll.common.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record LoginRequestDTO(
       String username,
       String password
) { }
