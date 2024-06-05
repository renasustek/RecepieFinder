package com.github.renas.recepieFinder.controller;

import com.github.renas.recepieFinder.requestBodies.IngredientsRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import com.github.renas.recepieFinder.service.RecipeMatcherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeMatcherController {
    private final RecipeMatcherService ingredientsService;

    public RecipeMatcherController(RecipeMatcherService ingredientsService){
        this.ingredientsService = ingredientsService;
    }

    @PostMapping
    public List<Recipe> getRecipes(@RequestBody IngredientsRequest ingredientsRequest){
        return ingredientsService.recipeSearch(ingredientsRequest);
    }
}
