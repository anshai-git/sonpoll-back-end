package com.sonpoll.oradea.sonpoll.common.environment;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.Callable;

@Data
@Builder
public class EnvironmentTask {
    private Callable<Void> onDevServer;
    private Callable<Void> onProdServer;
    private Callable<String> passOnDev;
    private Callable<String> passOnProd;
}
