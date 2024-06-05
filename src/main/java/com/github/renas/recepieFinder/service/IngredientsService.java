package com.github.renas.recepieFinder.service;

import com.github.renas.recepieFinder.other.IngredientsRequest;
import com.github.renas.recepieFinder.other.Recipe;
import com.github.renas.recepieFinder.persistance.ElasticsearchRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsService {

    private final ElasticsearchRepo elasticsearchRepo;

    public IngredientsService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public List<Recipe> recipeSearch(IngredientsRequest ingredientsRequest){
        elasticsearchRepo.getRecipes();
        return null;
    }
}
