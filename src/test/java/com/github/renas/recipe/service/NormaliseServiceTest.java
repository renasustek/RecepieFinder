package com.github.renas.recipe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.persistance.object_mappings.NormalisedMapping;
import com.github.renas.recipe.persistance.object_mappings.PreNormalisedMapping;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.*;

@ExtendWith(MockitoExtension.class)
class NormaliseServiceTest {

    @Mock
    public ElasticsearchRepo elasticsearchRepo;

    @InjectMocks
    public NormaliseService normaliseService;

    @Mock
    public RecipeService recipeService;

    String name = "Example";
    String description = "Example description";
    String serves = "2";
    List<String> ingredients = List.of("2 tbsp half-fat soured cream");
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));

    List<Ingredient<Quantity>> ingredientsQuantity =
            List.of(new Ingredient<>(new Volume(2.0, Unit.TABLESPOON), "half-fat soured cream"));
    Recipe recipe = new Recipe(name, description, ingredientsQuantity, steps, serves);
    NormalisedMapping normalisedMapping = new NormalisedMapping(
            UUID.randomUUID(), recipe.name(), recipe.description(), recipe.ingredients(), steps, serves);

    PreNormalisedMapping preNormalisedMapping =
            new PreNormalisedMapping(UUID.randomUUID(), name, description, ingredients, steps, serves);
    SearchHit<PreNormalisedMapping> searchHit =
            new SearchHit<>(null, null, null, 1.0f, null, null, null, null, null, null, preNormalisedMapping);
    SearchHits<PreNormalisedMapping> searchHits =
            new SearchHitsImpl<>(1L, TotalHitsRelation.OFF, 10, null, null, List.of(searchHit), null, null, null);

    @Test
    void whenCalledShouldNormaliseListProvidedByPersistance() {
        given(elasticsearchRepo.getAllRecipes()).willReturn(searchHits);
        given(recipeService.addRecipes(any(Recipe.class))).willReturn(recipe);

        List<Recipe> result = normaliseService.normalise();

        assertThat(result).containsExactly(recipe);

        assertThat(result.getFirst().name()).isEqualTo(name);
        assertThat(result.getFirst().description()).isEqualTo(description);
        assertThat(result.getFirst().steps()).isEqualTo(steps);
        assertThat(result.getFirst().serves()).isEqualTo(serves);
    }
}
