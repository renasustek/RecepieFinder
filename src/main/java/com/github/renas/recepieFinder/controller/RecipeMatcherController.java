package com.github.renas.recepieFinder.controller;

import com.github.renas.recepieFinder.requestBodies.FindRecipeRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import com.github.renas.recepieFinder.service.RecipeMatcherService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeMatcherController {

    private final RecipeMatcherService ingredientsService;

    public RecipeMatcherController(RecipeMatcherService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping
    public List<Recipe> getRecipes(@RequestBody FindRecipeRequest findRecipeRequest) {
        return ingredientsService.recipeSearch(findRecipeRequest);
    }
}
