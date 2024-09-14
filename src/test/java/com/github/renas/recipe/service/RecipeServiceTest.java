package com.github.renas.recipe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.persistance.object_mappings.NormalisedMapping;
import com.github.renas.recipe.request.FindRecipeRequest;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.ingredient.Ingredient;
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
    public RecipeService recipeService;

    List<String> mustIngredients = List.of("one", "two");
    List<String> shouldIngredients = List.of("one", "two");
    List<String> mustNotIngredients = List.of("one", "two");
    FindRecipeRequest validRequest = new FindRecipeRequest(mustIngredients, shouldIngredients, mustNotIngredients, 2);

    String name = "Example";
    String description = "Example description";
    String serves = "2";
    List<Ingredient<Quantity>> ingredients = Collections.emptyList();
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));
    Recipe recipe = new Recipe(name, description, ingredients, steps, serves);
    List<Recipe> recipes = List.of(recipe);

    NormalisedMapping normalisedMapping = new NormalisedMapping(
            UUID.randomUUID(), recipe.name(), recipe.description(), recipe.ingredients(), recipe.steps(), "2");
    SearchHit<NormalisedMapping> searchHit =
            new SearchHit<>(null, null, null, 1.0f, null, null, null, null, null, null, normalisedMapping);
    SearchHits<NormalisedMapping> searchHits =
            new SearchHitsImpl<>(1L, TotalHitsRelation.OFF, 10, null, null, List.of(searchHit), null, null, null);

    String mustIngredientsString = "one two ";
    String shouldIngredientsString = "one two ";
    String mustNotIngredientsString = "one two ";

    @Test
    void whenGivenValidRequestShouldReturnList() {
        given(elasticsearchRepo.getRecipes(mustIngredientsString, shouldIngredientsString, mustNotIngredientsString))
                .willReturn(searchHits);
        assertThat(recipeService.recipeSearch(validRequest).get(0).name())
                .isEqualTo(searchHit.getContent().getName());
        assertThat(recipeService.recipeSearch(validRequest).get(0).ingredients())
                .isEqualTo(searchHit.getContent().getIngredients());
    }

    @Test
    void whenGivenRequestAndNoRecipeFoundShouldReturnEmptyList() {
        SearchHits<NormalisedMapping> emptySearchHits = new SearchHitsImpl<>(
                1L, TotalHitsRelation.OFF, 10, null, null, Collections.emptyList(), null, null, null);
        given(elasticsearchRepo.getRecipes(mustIngredientsString, shouldIngredientsString, mustNotIngredientsString))
                .willReturn(emptySearchHits);
        assertTrue(recipeService.recipeSearch(validRequest).isEmpty());
    }

    @Test
    void whenGivenRecipeToAddShouldReturnAValidRecipe() {
        given(elasticsearchRepo.addRecipe(any(NormalisedMapping.class))).willReturn(normalisedMapping);

        Recipe addRecipe = recipeService.addRecipes(recipe);

        assertThat(addRecipe.name()).isEqualTo(normalisedMapping.getName());
        assertThat(addRecipe.description()).isEqualTo(normalisedMapping.getDescription());
        assertThat(addRecipe.ingredients()).isEqualTo(normalisedMapping.getIngredients());
        assertThat(addRecipe.steps()).isEqualTo(normalisedMapping.getSteps());
        assertThat(addRecipe.serves()).isEqualTo(normalisedMapping.getServes());
    }
}
