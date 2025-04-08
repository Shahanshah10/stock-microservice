package com.learn.usbank;

import com.learn.usbank.properties.DataStaxAstraProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Stock API", version = "1.0", description = "Manage stock data")
)
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class StockMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMicroserviceApplication.class, args);
    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
