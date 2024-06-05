package com.github.renas.recepieFinder.controller;

import com.github.renas.recepieFinder.other.IngredientsRequest;
import com.github.renas.recepieFinder.other.Recipe;
import com.github.renas.recepieFinder.service.IngredientsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService){
        this.ingredientsService = ingredientsService;
    }

    @PostMapping
    public List<Recipe> getRecipes(@RequestBody IngredientsRequest ingredientsRequest){
        return ingredientsService.recipeSearch(ingredientsRequest);
    }
}
