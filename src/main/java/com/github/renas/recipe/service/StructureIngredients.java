package com.github.renas.recipe.service;

import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.request.ingredient.Ingredient;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.renas.recipe.measurment.Unit.*;

@Component
public class StructureIngredients {

    //    public String ingredient;
    //
    //    public StructureIngredients(String ingredient) {
    //        this.ingredient = ingredient;
    //    }

    public static Ingredient<? extends Quantity> stringToQuantity(String ingredient) {
        String regex = "(\\d+/?\\d*|½|\\d+\\.\\d+)\\s*(\\w+(?:\\s*\\w+)*)\\s*(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ingredient);
        if (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String regexUnit = matcher.group(2);
            String ingredientName = matcher.group(3);


            return new Ingredient<>(parse(regexUnit, value), ingredientName);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Quantity> T parse(String unit, int value) {
        return switch (unit) {
            case "g", "gram", "grams" -> (T) new Mass(value, GRAM) ;
            case "kg", "kilogram", "kilograms" -> (T) new Mass(value, KILOGRAM);
            case "ml", "millilitre", "millilitres" -> (T) new Volume(value, MILLILITER);
            case "l", "litre", "litres" -> (T) new Volume(value, LITER);
            case "tbsp", "tablespoon", "tablespoons" -> (T) new Volume(value, TABLESPOON);
            default -> null;
        };
    }
}
