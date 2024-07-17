package com.github.renas.recipe.service;

import com.github.renas.recipe.persistance.ElasticsearchRepo;
import com.github.renas.recipe.request.Recipe;
import com.github.renas.recipe.request.TesterRecipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NormaliseService {

    private final ElasticsearchRepo elasticsearchRepo;

    public NormaliseService(ElasticsearchRepo elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    public List<TesterRecipe> normalise() {
        return elasticsearchRepo
                .getAllRecipes()
                .getSearchHits()
                .stream()
                .map(hit -> new TesterRecipe(
                        hit.getContent().getName(),
                        hit.getContent().getDescription(),
                        hit.getContent().getIngredients(),
                        hit.getContent().getSteps(),
                        hit.getContent().getServes()))
                .toList();
    }
}
