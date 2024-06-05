package com.github.renas.recepieFinder;

import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecepieFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecepieFinderApplication.class, args);
	}

}
