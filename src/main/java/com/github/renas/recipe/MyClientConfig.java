package com.github.renas.recipe;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class MyClientConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        String hostAndPort = "localhost:9200";
        return ClientConfiguration.builder().connectedTo(hostAndPort).build();
    }
}
