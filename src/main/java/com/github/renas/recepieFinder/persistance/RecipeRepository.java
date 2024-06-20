package com.github.renas.recepieFinder.persistance;

import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends ElasticsearchRepository<RecipeMapping, UUID> {
    @Query()
    List<RecipeMapping> findByName(String ingredients);
}
