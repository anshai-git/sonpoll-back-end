package com.sonpoll.oradea.sonpoll.common.environment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnvironmentType {
    DEV("DEV"),
    PROD("PROD");

    private String value;

    public static EnvironmentType fromValue(final String envType) {
        for(EnvironmentType env: EnvironmentType.values()) {
            if(envType.equals(env.value)) return env;
        }
        throw new IllegalArgumentException(envType);
    }
}
