package com.github.renas.recipe.controller;

import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.service.NormaliseService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/normalise", produces = MediaType.APPLICATION_JSON_VALUE)
public class NormaliseController {

    private final NormaliseService normaliseService;

    public NormaliseController(NormaliseService normaliseService) {
        this.normaliseService = normaliseService;
    }

    @PostMapping
    public List<Recipe> getRecipes() {
        return normaliseService.normalise();
    }
}
