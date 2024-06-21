package com.github.renas.recepieFinder.service;

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


    public RecipeMatcherService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public List<Recipe> recipeSearch(IngredientsRequest ingredientsRequest){
        StringBuilder mustIngredientsSb = new StringBuilder();
        StringBuilder shouldIngredientsSb = new StringBuilder();
        ingredientsRequest.mustIngredients().forEach( i -> mustIngredientsSb.append(i).append(" "));
        ingredientsRequest.shouldIngredients().forEach( i -> shouldIngredientsSb.append(i).append(" "));

        List<Recipe> recipes = new ArrayList<>();

        elasticsearchRepo.getRecipes(mustIngredientsSb.toString(), shouldIngredientsSb.toString())
                .getSearchHits().forEach(hit
                        -> recipes.add(new Recipe(hit.getContent().getName(),hit.getContent().getIngredients(),hit.getContent().getSteps())));


        return recipes; //todo make lines 30 - 37, one line.
    }
}
