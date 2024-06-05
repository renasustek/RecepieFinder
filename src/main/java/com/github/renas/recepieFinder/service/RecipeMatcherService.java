package com.github.renas.recepieFinder.service;

import com.github.renas.recepieFinder.requestBodies.IngredientsRequest;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeMatcherService {

    private final ElasticsearchRepo elasticsearchRepo;

    public RecipeMatcherService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public List<Recipe> recipeSearch(IngredientsRequest ingredientsRequest){
        StringBuilder sb = new StringBuilder();

        ingredientsRequest.mustIngredients().stream().map(ingredient -> ());
        SearchHits<RecipeMapping> callRepo = elasticsearchRepo.getRecipes(sb.toString());
        List<Recipe> recipes = new ArrayList<>();
        for (SearchHit<RecipeMapping> hit : callRepo) {
            recipes.add(new Recipe(hit.getContent().getName(),hit.getContent().getIngredients(),hit.getContent().getSteps()));
        }
        return recipes;
    }
}
