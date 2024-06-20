package com.github.renas.recepieFinder.service;

import com.github.renas.recepieFinder.persistance.RecipeRepository;
import com.github.renas.recepieFinder.requestBodies.IngredientsRequest;
import com.github.renas.recepieFinder.persistance.objectMappings.RecipeMapping;
import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import com.github.renas.recepieFinder.requestBodies.Recipe;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeMatcherService {

    private final ElasticsearchRepo elasticsearchRepo;

    private final RecipeRepository recepieRepository;

    public RecipeMatcherService(ElasticsearchRepo elasticsearchRepo, RecipeRepository recepieRepository) {
        this.elasticsearchRepo = elasticsearchRepo;
        this.recepieRepository = recepieRepository;
    }

    public List<Recipe> recipeSearch(IngredientsRequest ingredientsRequest){
        StringBuilder mustIngredientsSb = new StringBuilder();
        StringBuilder shouldIngredientsSb = new StringBuilder();
        for (String ingredient: ingredientsRequest.mustIngredients()){
            mustIngredientsSb.append(ingredient).append(" ");
        }
        for (String ingredient: ingredientsRequest.shouldIngredients()){
            shouldIngredientsSb.append(ingredient).append(" ");
        }
        SearchHits<RecipeMapping> callRepo = elasticsearchRepo.getRecipes(mustIngredientsSb.toString(), shouldIngredientsSb.toString());
        List<Recipe> recipes = new ArrayList<>();
        for (SearchHit<RecipeMapping> hit : callRepo) {
            recipes.add(new Recipe(hit.getContent().getName(),hit.getContent().getIngredients(),hit.getContent().getSteps()));
        }

        return recipes;
    }
}
