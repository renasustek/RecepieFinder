package com.github.renas.recipe;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

class MyClientConfigTest {

    private MyClientConfig myClientConfig = new MyClientConfig();

    @Test
    void clientConfigurationShouldReturnCorrectHost() {
        ClientConfiguration clientConfiguration = myClientConfig.clientConfiguration();

        List<String> endpoints = clientConfiguration.getEndpoints().stream()
                .map(InetSocketAddress::toString)
                .collect(Collectors.toList());

        assertThat(endpoints).containsExactly("localhost/<unresolved>:9200");
    }
}
