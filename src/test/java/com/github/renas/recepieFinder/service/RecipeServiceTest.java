package com.github.renas.recepieFinder.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import com.github.renas.recepieFinder.requestBodies.FindRecipeRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    public ElasticsearchRepo elasticsearchRepo;

    @InjectMocks
    public RecipeService recipeMatcherService;

    List<String> mustIngredients = List.of("one", "two");
    List<String> shouldIngredients = List.of("one", "two");
    List<String> mustNotIngredients = List.of("one", "two");
    FindRecipeRequest validRequest = new FindRecipeRequest(mustIngredients, shouldIngredients, mustNotIngredients, 2);

    String name = "Example";
    String description = "Example description";
    String serves = "2";
    List<String> ingredients = new ArrayList<>(List.of("one", "two", "three"));
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));
    Recipe recipe = new Recipe(name, description,ingredients, steps, serves);
    List<Recipe> recipes = List.of(recipe);

    RecipeMapping recipeMapping =
            new RecipeMapping(UUID.randomUUID(), recipe.name(), "description", recipe.ingredients(), recipe.steps(),"2");
    SearchHit<RecipeMapping> searchHit =
            new SearchHit<>(null, null, null, 1.0f, null, null, null, null, null, null, recipeMapping);
    SearchHits<RecipeMapping> searchHits =
            new SearchHitsImpl<>(1L, TotalHitsRelation.OFF, 10, null, null, List.of(searchHit), null, null, null);

    String mustIngredientsString = "one two ";
    String shouldIngredientsString = "one two ";
    String mustNotIngredientsString = "one two ";

    @Test
    void whenGivenValidRequestShouldReturnList() {
        given(elasticsearchRepo.getRecipes(mustIngredientsString, shouldIngredientsString, mustNotIngredientsString))
                .willReturn(searchHits);
        assertThat(recipeMatcherService.recipeSearch(validRequest).get(0).name())
                .isEqualTo(searchHit.getContent().getName());
        assertThat(recipeMatcherService.recipeSearch(validRequest).get(0).ingredients())
                .isEqualTo(searchHit.getContent().getIngredients());
    }

    @Test
    void whenGivenRequestAndNoRecipeFoundShouldReturnEmptyList() {
        SearchHits<RecipeMapping> searchHits = new SearchHitsImpl<>(
                1L, TotalHitsRelation.OFF, 10, null, null, Collections.emptyList(), null, null, null);
        given(elasticsearchRepo.getRecipes(mustIngredientsString, shouldIngredientsString, mustNotIngredientsString))
                .willReturn(searchHits);
        assertTrue(recipeMatcherService.recipeSearch(validRequest).isEmpty());
    }
}
