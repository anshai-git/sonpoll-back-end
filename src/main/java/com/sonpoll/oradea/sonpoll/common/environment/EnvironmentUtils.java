package com.sonpoll.oradea.sonpoll.common.environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EnvironmentUtils {
    public static void handleTask(final String envType, final EnvironmentTask task) throws Exception {
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<Void> future = switch (EnvironmentType.valueOf(envType)) {
            case DEV -> executor.submit(task.getOnDevServer());
            case PROD -> executor.submit(task.getOnProdServer());

            default -> throw new IllegalArgumentException(envType);
        };

        future.get();
    }

    public static String handlePasswordByEnv(final String envType, final EnvironmentTask task) throws Exception {
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> future = switch (EnvironmentType.valueOf(envType)) {
            case DEV -> executor.submit(task.getPassOnDev());
            case PROD -> executor.submit(task.getPassOnProd());

            default -> throw new IllegalArgumentException(envType);
        };

        return future.get();
    }
}
