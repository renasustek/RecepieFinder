package com.github.renas.recipe.service;

import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.request.Recipe;
import java.util.List;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

@Service
public class NormaliseService {

    private final ElasticsearchRepo elasticsearchRepo;

    private final RecipeService recipeService;

    public NormaliseService(ElasticsearchRepo elasticsearchRepo, RecipeService recipeService) {
        this.elasticsearchRepo = elasticsearchRepo;
        this.recipeService = recipeService;
    }

    public List<Recipe> normalise() {

        List<Recipe> normalisedRecipes = elasticsearchRepo.getAllRecipes().getSearchHits().stream()
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

        return normalisedRecipes.stream().map(recipeService::addRecipes).toList();
    }
}
