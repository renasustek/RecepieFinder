package com.github.renas.recepieFinder.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renas.recepieFinder.requestBodies.FindRecipeRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import com.github.renas.recepieFinder.service.RecipeMatcherService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RecipeMatcherController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RecipeMatcherControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RecipeMatcherService service;

    @Autowired
    private ObjectMapper objectMapper;

    String name = "Example";
    List<String> ingredients = new ArrayList<>(List.of("one", "two", "three"));
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));
    Recipe recipe = new Recipe(name, ingredients, steps);
    List<Recipe> recipes = List.of(recipe);

    List<String> mustIngredients = List.of("one", "two");
    List<String> shouldIngredients = List.of("one", "two");
    List<String> mustNotIngredients = List.of("one", "two");

    FindRecipeRequest validRequest = new FindRecipeRequest(mustIngredients, shouldIngredients, mustNotIngredients);

    FindRecipeRequest emptyRequest =
            new FindRecipeRequest(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

    List<String> invalidRequest = List.of("invalidRequest");

    @Test
    void whenGivenValidRequestReturnListOfRecipes() throws Exception {
        when(service.recipeSearch(validRequest)).thenReturn(recipes);
        mvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(recipe.name()));
    }

    @Test
    void whenGivenEmptyRequestReturnEmptyListOfRecipes() throws Exception {
        when(service.recipeSearch(emptyRequest)).thenReturn(Collections.emptyList());
        mvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    void validRequestButNothingFoundShouldReturnEmptyList() throws Exception {
        when(service.recipeSearch(validRequest)).thenReturn(Collections.emptyList());
        mvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }
}
