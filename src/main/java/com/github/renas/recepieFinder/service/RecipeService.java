package com.github.renas.recepieFinder.service;

import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import com.github.renas.recepieFinder.requestBodies.FindRecipeRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final ElasticsearchRepo elasticsearchRepo;

    private int serves;

    public RecipeService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public Recipe addRecipes(Recipe recipe) {

        RecipeMapping recipeMapping = elasticsearchRepo.addRecipe(new RecipeMapping(
                UUID.randomUUID(),
                recipe.name(),
                recipe.description(),
                recipe.ingredients(),
                recipe.steps(),
                recipe.serves()
        ));
        return new Recipe(recipeMapping.getName(),recipeMapping.getDescription(),recipeMapping.getIngredients(),recipeMapping.getSteps(),recipeMapping.getServes());
    }


    public List<Recipe> recipeSearch(FindRecipeRequest findRecipeRequest) {
        StringBuilder mustIngredientsSb = new StringBuilder();
        StringBuilder shouldIngredientsSb = new StringBuilder();
        StringBuilder mustNotIngredientsSb = new StringBuilder();
        this.serves = findRecipeRequest.serves();
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

    private List<String> refactorToFitServing(List<String> ingredients, String currentRecipeServes) {
        int serves = Integer.parseInt(currentRecipeServes);
        for (String ingredient : ingredients) {

        }

        return ingredients;
    }
}
