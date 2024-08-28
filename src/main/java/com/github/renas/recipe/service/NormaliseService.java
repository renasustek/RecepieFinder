package com.github.renas.recipe.service;

import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.request.Recipe;
import java.util.List;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

@Service
public class NormaliseService {

    private final ElasticsearchRepo elasticsearchRepo;

    public NormaliseService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public List<Recipe> normalise() {
        return elasticsearchRepo.getAllRecipes().getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(content -> new Recipe(
                        content.getName(),
                        content.getDescription(),
                        content.getIngredients().stream()
                                .map(StructureIngredients::stringToQuantity)
                                .toList(),
                        content.getSteps(),
                        content.getServes()))
                .toList();
    }
}
