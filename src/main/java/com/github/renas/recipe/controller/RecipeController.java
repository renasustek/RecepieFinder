package com.github.renas.recipe.controller;

import com.github.renas.recipe.request.FindRecipeRequest;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.service.RecipeService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
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
