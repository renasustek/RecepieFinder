package com.github.renas.recepieFinder.controller;

import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import com.github.renas.recepieFinder.requestBodies.FindRecipeRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import com.github.renas.recepieFinder.service.RecipeService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

    private final RecipeService ingredientsService;

    public RecipeController(RecipeService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping("/search")
    public List<Recipe> getRecipes(@RequestBody FindRecipeRequest findRecipeRequest) {
        return ingredientsService.recipeSearch(findRecipeRequest);
    }

    @PostMapping("/create")
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return ingredientsService.addRecipes(recipe);
    }
}
