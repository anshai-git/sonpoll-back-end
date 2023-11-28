package com.sonpoll.oradea.sonpoll.common.environment.profiler;

public interface EnvironmentTask {
    String getPassword(String plainPassword);

    void sendResetPasswordEmail(String emailAddress, String resetLink);
}
