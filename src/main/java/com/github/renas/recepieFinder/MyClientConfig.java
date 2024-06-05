package com.github.renas.recepieFinder;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class MyClientConfig extends ElasticsearchConfiguration {

	@Override
	public  ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()           
			.connectedTo("localhost:9200")
				.usingSsl("77:01:0B:20:4E:94:4F:27:03:9A:87:0D:EB:F0:11:F6:AE:FB:F9:F5:7A:41:B8:A6:55:2C:9E:5F:D8:B2:1B:64")
				.withBasicAuth("elastic", "elastic")
			.build();
	}
}