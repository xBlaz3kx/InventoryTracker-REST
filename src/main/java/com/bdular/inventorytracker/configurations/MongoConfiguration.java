package com.bdular.inventorytracker.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    public String databaseUrl;

    @Value("${spring.data.mongodb.database}")
    public String databaseName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(databaseUrl);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), databaseName);
    }

}