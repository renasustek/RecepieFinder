package com.github.renas.recipe.service;

import static com.github.renas.recipe.measurment.Unit.*;

import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StructureIngredients {

    //    public String ingredient;
    //
    //    public StructureIngredients(String ingredient) {
    //        this.ingredient = ingredient;
    //    }

    //    public static <T extends Quantity> Ingredient<T> stringToQuantity(String ingredient) {
    //        String regex = "(\\d+\\s?\\d*/?\\d*)?\\s*(\\w+)?\\s*(.+)";
    //        Pattern pattern = Pattern.compile(regex);
    //        Matcher matcher = pattern.matcher(ingredient);
    //        if (matcher.find()) {
    //            double value = parseQuantity(matcher.group(1));
    //            String regexUnit = matcher.group(2);
    //            String ingredientName = matcher.group(3);
    //
    //
    //            return new Ingredient<>(parse(regexUnit, value), ingredientName);
    //        }
    //        return null;
    //    }
    //
    //
    //    private static double parseQuantity(String quantityStr){
    //       if (quantityStr.contains("/")) {
    //            String[] parts = quantityStr.split("/");
    //            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
    //        } else {
    //            return Double.parseDouble(quantityStr);
    //        }
    //    }

    public static <T extends Quantity> Ingredient<T> stringToQuantity(String ingredient) {
        ArrayList<String> units = new ArrayList<>(List.of(
                "g", "gram", "kg", "kilogram", "ml", "millilitre", "litre", "tablespoon", "tbsp", "teaspoon", "tsp"));

        String unitFound = null;
        int unitIndex = -1;

        for (String unit : units) {
            unitIndex = ingredient.indexOf(unit);
            if (unitIndex != -1) {
                unitFound = unit;
                break;
            }
        }

        if (unitFound == null) {
            return null;
        }

        double amount;
        try {
            amount = Double.parseDouble(ingredient.substring(0, unitIndex).trim());
        } catch (NumberFormatException e) {
            return null;
        }
        String name = ingredient.substring(unitIndex + unitFound.length()).trim();

        return new Ingredient<>(parse(unitFound, amount), name);
    }

    private static Double amountParser(String amount) {
        return Double.parseDouble(amount);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Quantity> T parse(String unit, double value) {
        return switch (unit) {
            case "g", "gram", "grams" -> (T) new Mass(value, GRAM);
            case "kg", "kilogram", "kilograms" -> (T) new Mass(value, KILOGRAM);
            case "ml", "millilitre", "millilitres" -> (T) new Volume(value, MILLILITER);
            case "l", "litre", "litres" -> (T) new Volume(value, LITER);
            case "tbsp", "tablespoon", "tablespoons" -> (T) new Volume(value, TABLESPOON);
            case "tsp", "teaspoon", "teaspoons" -> (T) new Volume(value, TEASPOON);
            default -> null;
        };
    }
}
