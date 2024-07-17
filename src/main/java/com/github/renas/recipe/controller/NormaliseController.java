package com.github.renas.recipe.controller;

import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.TesterRecipe;
import com.github.renas.recipe.service.NormaliseService;
import com.github.renas.recipe.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/normalise", produces = MediaType.APPLICATION_JSON_VALUE)
public class NormaliseController {

    private final NormaliseService normaliseService;

    public NormaliseController(NormaliseService normaliseService) {
        this.normaliseService = normaliseService;
    }

    @PostMapping
    public List<TesterRecipe> getRecipes() {
        return normaliseService.normalise();
    }

}
