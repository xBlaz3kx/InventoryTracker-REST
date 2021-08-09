package com.bdular.inventorytracker;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.bdular.inventorytracker", exclude = {DataSourceAutoConfiguration.class})
@EnableBatchProcessing
@EnableScheduling
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMongoRepositories
@EnableSwagger2
@EnableWebMvc
@EnableAsync
public class InventoryTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryTrackerApplication.class, args);
    }
}
