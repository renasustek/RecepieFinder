package com.github.renas.recepieFinder.persistance;

import com.github.renas.recepieFinder.other.Recipe;
import com.github.renas.recepieFinder.other.RecipeMapping;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class ElasticsearchRepo {

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchRepo(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void getRecipes(){
        Query query = new StringQuery("{ \"match\": { \"ingredients\": { \"query\": \"oil\" } } } ");
        SearchHits<RecipeMapping> searchHits = elasticsearchOperations.search(query, RecipeMapping.class);
        }

}
