package com.github.renas.recipe.persistance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.persistance.object_mappings.NormalisedMapping;
import com.github.renas.recipe.persistance.object_mappings.PreNormalisedMapping;
import com.github.renas.recipe.request.FindRecipeRequest;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

@ExtendWith(MockitoExtension.class) // Ensure Mockito extension is used
class ElasticsearchRepoTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private ElasticsearchRepo elasticsearchRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRecipeShouldReturnSavedRecipe() {
        Recipe recipe = getRecipe();
        NormalisedMapping normalisedMapping = new NormalisedMapping(
                UUID.randomUUID(), recipe.name(), recipe.description(), recipe.ingredients(), recipe.steps(), "2");

        when(elasticsearchOperations.save(normalisedMapping)).thenReturn(normalisedMapping);

        NormalisedMapping result = elasticsearchRepo.addRecipe(normalisedMapping);

        assertNotNull(result);
        assertEquals(recipe.description(), result.getDescription());
        assertEquals(recipe.name(), result.getName());
        assertEquals(recipe.steps(), result.getSteps());
        assertEquals(recipe.serves(), result.getServes());
        verify(elasticsearchOperations, times(1)).save(normalisedMapping);
    }

    private static Recipe getRecipe() {
        List<String> mustIngredients = List.of("one", "two");
        List<String> shouldIngredients = List.of("one", "two");
        List<String> mustNotIngredients = List.of("one", "two");
        FindRecipeRequest validRequest =
                new FindRecipeRequest(mustIngredients, shouldIngredients, mustNotIngredients, 2);

        String name = "Example";
        String description = "Example description";
        String serves = "2";
        List<Ingredient<Quantity>> ingredients = Collections.emptyList();
        List<String> steps = new ArrayList<>(List.of("one", "two", "three"));
        Recipe recipe = new Recipe(name, description, ingredients, steps, serves);
        return recipe;
    }

    @Test
    void getAllRecipesShouldReturnSearchHits() {
        SearchHits<PreNormalisedMapping> searchHits = mock(SearchHits.class);
        when(elasticsearchOperations.search(any(Query.class), eq(PreNormalisedMapping.class)))
                .thenReturn(searchHits);

        SearchHits<PreNormalisedMapping> result = elasticsearchRepo.getAllRecipes();

        assertNotNull(result);
        assertEquals(searchHits, result);
        verify(elasticsearchOperations, times(1)).search(any(Query.class), eq(PreNormalisedMapping.class));
    }

    @Test
    void getRecipesShouldReturnSearchHits() {
        String mustIngredients = "chicken";
        String shouldIngredients = "onion";
        String mustNotIngredients = "garlic";

        SearchHits<NormalisedMapping> searchHits = mock(SearchHits.class);
        when(elasticsearchOperations.search(any(Query.class), eq(NormalisedMapping.class)))
                .thenReturn(searchHits);

        SearchHits<NormalisedMapping> result =
                elasticsearchRepo.getRecipes(mustIngredients, shouldIngredients, mustNotIngredients);

        assertNotNull(result);
        assertEquals(searchHits, result);
        verify(elasticsearchOperations, times(1)).search(any(Query.class), eq(NormalisedMapping.class));
    }
}
