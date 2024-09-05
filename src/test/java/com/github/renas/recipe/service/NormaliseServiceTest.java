package com.github.renas.recipe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.persistance.ElasticsearchRepo;
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
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;

@ExtendWith(MockitoExtension.class)
class NormaliseServiceTest {

    @Mock
    public ElasticsearchRepo elasticsearchRepo;

    @InjectMocks
    public NormaliseService normaliseService;

    String name = "Example";
    String description = "Example description";
    String serves = "2";
    List<String> ingredients = List.of("2 tbsp half-fat soured cream");
    List<String> steps = new ArrayList<>(List.of("one", "two", "three"));

    List<Ingredient<Quantity>> ingredientsQuantity =
            List.of(new Ingredient<>(new Volume(2.0, Unit.TABLESPOON), "half-fat soured cream"));

    List<Recipe> validRecipeList =
            new ArrayList<>(List.of(new Recipe(name, description, ingredientsQuantity, steps, serves)));

    PreNormalisedMapping preNormalisedMapping =
            new PreNormalisedMapping(UUID.randomUUID(), name, description, ingredients, steps, serves);
    SearchHit<PreNormalisedMapping> searchHit =
            new SearchHit<>(null, null, null, 1.0f, null, null, null, null, null, null, preNormalisedMapping);
    SearchHits<PreNormalisedMapping> searchHits =
            new SearchHitsImpl<>(1L, TotalHitsRelation.OFF, 10, null, null, List.of(searchHit), null, null, null);

    @Test
    void whenCalledShouldNormaliseListProvidedByPersistance() {
        given(elasticsearchRepo.getAllRecipes()).willReturn(searchHits);
        assertThat(normaliseService
                        .normalise()
                        .getFirst()
                        .ingredients()
                        .getFirst()
                        .getQuantity()
                        .toString())
                .isEqualTo(ingredientsQuantity.getFirst().getQuantity().toString());
        assertThat(normaliseService
                        .normalise()
                        .getFirst()
                        .ingredients()
                        .getFirst()
                        .getName())
                .isEqualTo(ingredientsQuantity.getFirst().getName());
    }
}
