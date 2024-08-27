package com.github.renas.recipe.service;

import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.TesterRecipe;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NormaliseService {

    private final ElasticsearchRepo elasticsearchRepo;

    private final RecipeService recipeService;


    public NormaliseService(ElasticsearchRepo elasticsearchRepo, RecipeService recipeService) {
        this.elasticsearchRepo = elasticsearchRepo;
        this.recipeService = recipeService;
    }

    public List<Recipe> normalise() {
       return elasticsearchRepo
                .getAllRecipes()
                .getSearchHits()
                .stream()
               .map(SearchHit::getContent)
                .map(content -> new Recipe(
                        content.getName(),
                        content.getDescription(),
                        content.getIngredients().stream().map(
                                StructureIngredients::stringToQuantity
                        ).toList(),
                        content.getSteps(),
                        content.getServes()))
                .toList();

    }
}
