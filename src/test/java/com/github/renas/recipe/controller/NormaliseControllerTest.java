package com.github.renas.recipe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.ingredient.Ingredient;
import com.github.renas.recipe.service.NormaliseService;
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

@WebMvcTest(controllers = NormaliseController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class NormaliseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NormaliseService service;

    @Autowired
    private ObjectMapper objectMapper;

    String name = "Example";
    String description = "Example description";
    String serves = "2";
    List<Ingredient<Quantity>> ingredients = Collections.emptyList();
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));
    Recipe recipe = new Recipe(name, description, ingredients, steps, serves);
    List<Recipe> recipes = List.of(recipe);

    @Test
    void whenCalledShouldReturnAllRecipes() throws Exception {
        when(service.normalise()).thenReturn(recipes);
        mvc.perform(post("/normalise").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(recipe.name()))
                .andExpect(jsonPath("$[0].description").value(recipe.description()));
    }
}
