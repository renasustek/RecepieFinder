package com.github.renas.recepieFinder.persistance;

import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchRepo {

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchRepo(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public SearchHits<RecipeMapping> getRecipes(String ingredients){
        String queryString = String.format("{ \"match\": { \"ingredients\": { \"query\": \"%s\" } } }", ingredients);
        Query query = new StringQuery(queryString);
        return elasticsearchOperations.search(query, RecipeMapping.class);
    }

}
