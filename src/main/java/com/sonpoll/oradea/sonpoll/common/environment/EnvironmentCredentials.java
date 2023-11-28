package com.sonpoll.oradea.sonpoll.common.environment;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:env_dev.properties")
public class EnvironmentCredentials {
    /**
     * uncomment if you have env_dev.properties
     */
//  @Value("${spring.data.mongodb.password}")
//  private String mongoDbPassword;

    @Value("${sendGrid.password}")
    private String sendGridPassword;

//    @Value("${sendGrid.config.mail.pass}")
//    private String sendGridEmailPassword;

//  @Value("${spring.data.mongodb.uri}")
//  private String mongoDbRemoteLink;

}
