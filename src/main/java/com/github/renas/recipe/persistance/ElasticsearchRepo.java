package com.github.renas.recipe.persistance;

import com.github.renas.recipe.persistance.object_mappings.NormalisedMapping;
import com.github.renas.recipe.persistance.object_mappings.PreNormalisedMapping;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchRepo {

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchRepo(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public NormalisedMapping addRecipe(NormalisedMapping recipe) {
        return elasticsearchOperations.save(recipe);
    }

    public SearchHits<PreNormalisedMapping> getAllRecipes() {
        Query query = NativeQuery.builder().withQuery(q -> q.matchAll(ma -> ma)).build();
        return elasticsearchOperations.search(query, PreNormalisedMapping.class);
    }

    public SearchHits<NormalisedMapping> getRecipes(
            String mustIngredients, String shouldIngredients, String mustNotIngredients) {
        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> {
                    String value = "ingredients";
                    return b.must(m -> m.match(ma -> ma.field(value).query(mustIngredients)))
                            .should(s -> s.match(sh -> sh.field(value).query(shouldIngredients)))
                            .mustNot(m -> m.match(ma -> ma.field(value).query(mustNotIngredients)));
                }))
                .build();

        return elasticsearchOperations.search(query, NormalisedMapping.class);
    }
}
