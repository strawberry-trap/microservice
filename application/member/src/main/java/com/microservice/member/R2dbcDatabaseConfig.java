package com.microservice.member;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

public class R2dbcDatabaseConfig {}

//@Configuration
//@EnableR2dbcRepositories
//public class R2dbcDatabaseConfig extends AbstractR2dbcConfiguration {
//
//    @Override
//    @Bean
//    public ConnectionFactory connectionFactory() {
//
//        ConnectionFactory pooledConnectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
//                .option(DRIVER,"pool")
//                .option(PROTOCOL,"mysql") // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
//                .option(HOST,"localhost")
//                .option(PORT,3306)
//                .option(USER,"root")
//                .option(PASSWORD,"dyddjs80")
//                .option(DATABASE,"microservice")
//                .build());
//
//        return pooledConnectionFactory;
//    }
//}
