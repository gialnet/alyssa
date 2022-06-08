package com.vivaldi.spring.alyssa.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
@Profile("dev")
public class ConfigDev {

    @Bean
    public RestHighLevelClient client(){
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo("rh8-nodo1:9200","rh8-nodo2:9200", "rh8-nodo3:9200")
                .withBasicAuth("elastic", "CJj1SfgRF3MTCeAhS1gL")
                .build();


        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }

}
