package com.sonpoll.oradea.sonpoll.common.environment;

import java.util.concurrent.Callable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvironmentTask {
    private Callable<Void> onDevServder;
    private Callable<Void> onProdServder;
}
