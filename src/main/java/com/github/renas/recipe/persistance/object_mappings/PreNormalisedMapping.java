package com.github.renas.recipe.persistance.object_mappings;

import java.util.List;
import java.util.UUID;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sample")
public class PreNormalisedMapping extends RecipeMapping {

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<String> ingredients;

    public PreNormalisedMapping(
            UUID id, String name, String description, List<String> ingredients, List<String> steps, String serves) {
        super(id, name, description, steps, serves);
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
