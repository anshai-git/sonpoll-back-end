package com.sonpoll.oradea.sonpoll.common.environment;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource(ignoreResourceNotFound=true,value="classpath:env_${environment}.properties")
public class EnvironmentCredentials {

    @Value("${spring.data.mongodb.password}")
    private String prop;

}
