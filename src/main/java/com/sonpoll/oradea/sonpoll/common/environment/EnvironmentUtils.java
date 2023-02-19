package com.sonpoll.oradea.sonpoll.common.environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EnvironmentUtils {
    public static void handleTask(final String envType, final EnvironmentTask task) throws Exception {
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<Void> future = switch (EnvironmentType.valueOf(envType)) {
            case DEV -> executor.submit(task.getOnDevServder());
            case PROD -> executor.submit(task.getOnProdServder());

            default -> throw new IllegalArgumentException(envType);
        };

        future.get();
    }
}
