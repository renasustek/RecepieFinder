package com.github.renas.recipe.service;

import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.persistance.object_mappings.NormalisedMapping;
import com.github.renas.recipe.request.FindRecipeRequest;
import com.github.renas.recipe.request.Recipe;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final ElasticsearchRepo elasticsearchRepo;

    public RecipeService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public Recipe addRecipes(Recipe recipe) {

        NormalisedMapping normalisedMapping = elasticsearchRepo.addRecipe(new NormalisedMapping(
                UUID.randomUUID(),
                recipe.name(),
                recipe.description(),
                recipe.ingredients(),
                recipe.steps(),
                recipe.serves()));
        return new Recipe(
                normalisedMapping.getName(),
                normalisedMapping.getDescription(),
                normalisedMapping.getIngredients(),
                normalisedMapping.getSteps(),
                normalisedMapping.getServes());
    }

    public List<Recipe> recipeSearch(FindRecipeRequest findRecipeRequest) {
        StringBuilder mustIngredientsSb = new StringBuilder();
        StringBuilder shouldIngredientsSb = new StringBuilder();
        StringBuilder mustNotIngredientsSb = new StringBuilder();

        findRecipeRequest
                .mustIngredients()
                .forEach(i -> mustIngredientsSb.append(i).append(" "));
        findRecipeRequest
                .shouldIngredients()
                .forEach(i -> shouldIngredientsSb.append(i).append(" "));
        findRecipeRequest
                .mustNotIngredients()
                .forEach(i -> mustNotIngredientsSb.append(i).append(" "));

        return elasticsearchRepo
                .getRecipes(
                        mustIngredientsSb.toString(), shouldIngredientsSb.toString(), mustNotIngredientsSb.toString())
                .getSearchHits()
                .stream()
                .map(hit -> new Recipe(
                        hit.getContent().getName(),
                        hit.getContent().getDescription(),
                        hit.getContent().getIngredients(),
                        hit.getContent().getSteps(),
                        hit.getContent().getServes()))
                .toList();
    }
}
