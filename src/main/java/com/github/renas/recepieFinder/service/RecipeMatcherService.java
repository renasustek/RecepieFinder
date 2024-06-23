package com.github.renas.recepieFinder.service;

import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import com.github.renas.recepieFinder.requestBodies.IngredientsRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecipeMatcherService {

    private final ElasticsearchRepo elasticsearchRepo;

    public RecipeMatcherService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public List<Recipe> recipeSearch(IngredientsRequest ingredientsRequest) {
        StringBuilder mustIngredientsSb = new StringBuilder();
        StringBuilder shouldIngredientsSb = new StringBuilder();
        ingredientsRequest
                .mustIngredients()
                .forEach(i -> mustIngredientsSb.append(i).append(" "));
        ingredientsRequest
                .shouldIngredients()
                .forEach(i -> shouldIngredientsSb.append(i).append(" "));

        return elasticsearchRepo
                .getRecipes(mustIngredientsSb.toString(), shouldIngredientsSb.toString())
                .getSearchHits()
                .stream()
                .map(hit -> new Recipe(
                        hit.getContent().getName(),
                        hit.getContent().getIngredients(),
                        hit.getContent().getSteps()))
                .toList();

    }
}
