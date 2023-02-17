package com.sonpoll.oradea.sonpoll.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Environment {
    DEV("DEV"),
    PROD("PROD");

    private String value;

    public static Environment fromValue(final String envType) {
        for(Environment env: Environment.values()) {
            if(envType.equals(env.value)) return env;
        }
        throw new IllegalArgumentException(envType);
    }
}
