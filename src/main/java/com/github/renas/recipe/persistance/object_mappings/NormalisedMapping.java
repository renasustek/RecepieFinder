package com.github.renas.recipe.persistance.object_mappings;

import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.util.List;
import java.util.UUID;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sample5")
public class NormalisedMapping extends RecipeMapping {

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Ingredient<Quantity>> ingredients;

    public NormalisedMapping(
            UUID id,
            String name,
            String description,
            List<Ingredient<Quantity>> ingredients,
            List<String> steps,
            String serves) {
        super(id, name, description, steps, serves);
        this.ingredients = ingredients;
    }

    public List<Ingredient<Quantity>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient<Quantity>> ingredients) {
        this.ingredients = ingredients;
    }
}
