package com.github.renas.recepieFinder.service;

import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import com.github.renas.recepieFinder.requestBodies.IngredientsRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)

class RecipeMatcherServiceTest {

    @Mock
    public ElasticsearchRepo elasticsearchRepo;

    @InjectMocks
    public RecipeMatcherService recipeMatcherService;


    List<String> mustIngredients = List.of("one","two");
    List<String> shouldIngredients = List.of("one","two");
    IngredientsRequest validRequest = new IngredientsRequest(mustIngredients ,shouldIngredients);


    String name = "Example";
    List<String> ingredients = new ArrayList<>(List.of("one", "two", "three"));
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));
    Recipe recipe = new Recipe(name, ingredients, steps);
    List<Recipe> recipes = List.of(recipe);

    RecipeMapping recipeMapping = new RecipeMapping(UUID.randomUUID(), recipe.name(), "description", recipe.ingredients(), recipe.steps());
    SearchHit<RecipeMapping> searchHit = new SearchHit<>(null,null, null, 1.0f, null, null, null, null, null, null, recipeMapping);
    SearchHits<RecipeMapping> searchHits = new SearchHitsImpl<>(1L, TotalHitsRelation.OFF, 10, null,null, List.of(searchHit),null,null,null);



    String mustIngredientsString = "one two ";
    String shouldIngredientsString = "one two ";

    @Test
    void whenGivenValidRequestShouldReturnList(){
        given(elasticsearchRepo.getRecipes(mustIngredientsString,shouldIngredientsString)).willReturn(searchHits);
        assertThat(recipeMatcherService.recipeSearch(validRequest).get(0).name()).isEqualTo(searchHit.getContent().getName());
        assertThat(recipeMatcherService.recipeSearch(validRequest).get(0).ingredients()).isEqualTo(searchHit.getContent().getIngredients());
    }

    @Test
    void whenGivenRequestAndNoRecipeFoundShouldReturnEmptyList(){
        SearchHits<RecipeMapping> searchHits = new SearchHitsImpl<>(1L, TotalHitsRelation.OFF, 10, null,null, Collections.emptyList(),null,null,null);
        given(elasticsearchRepo.getRecipes(mustIngredientsString,shouldIngredientsString)).willReturn(searchHits);
        assertTrue(recipeMatcherService.recipeSearch(validRequest).isEmpty());
    }
}