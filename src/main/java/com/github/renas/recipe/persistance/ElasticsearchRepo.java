package com.github.renas.recipe.persistance;

import com.github.renas.recipe.persistance.objectMappings.NormaliseMapping;
import com.github.renas.recipe.persistance.objectMappings.RecipeMapping;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

@Component
public class ElasticsearchRepo {

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchRepo(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public RecipeMapping addRecipe(RecipeMapping recipe) {
        return elasticsearchOperations.save(recipe);
    }

    public SearchHits<NormaliseMapping> getAllRecipes(){
        Query query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(ma -> ma))
                .build();
        return elasticsearchOperations.search(query, NormaliseMapping.class);

    }

    public SearchHits<RecipeMapping> getRecipes(
            String mustIngredients, String shouldIngredients, String mustNotIngredients) {
        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b.must(
                                m -> m.match(ma -> ma.field("ingredients").query(mustIngredients)))
                        .should(s -> s.match(sh -> sh.field("ingredients").query(shouldIngredients)))
                        .mustNot(m -> m.match(ma -> ma.field("ingredients").query(mustNotIngredients)))))
                .build();


        return elasticsearchOperations.search(query, RecipeMapping.class);
    }
}
