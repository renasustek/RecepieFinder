package com.github.renas.recipe.service;

import static com.github.renas.recipe.measurment.Unit.*;

import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.NoUnit;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StructureIngredients {

    public static Ingredient<Quantity> stringToQuantity(String ingredient) {
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

        double amount;
        String name;

        if (unitFound != null) {
            String amountStr = ingredient.substring(0, unitIndex).trim();
            try {
                amount = amountStringToDouble(amountStr);
            } catch (NumberFormatException e) {
                return null;
            }
            name = ingredient.substring(unitIndex + unitFound.length()).trim();
        } else {
            Ingredient<NoUnit> noUnit = noUnitFound(ingredient);
            return new Ingredient<>(noUnit.getQuantity(), noUnit.getName());
        }

        return new Ingredient<>(parse(unitFound, amount), name);
    }

    private static Ingredient<NoUnit> noUnitFound(String ingredient) {
        int index = -1;

        for (int i = 0; i < ingredient.length(); i++) {
            if (!Character.isDigit(ingredient.charAt(i))) {
                index = i;
                break;
            }
        }
        if (index == 0) {
            return new Ingredient<>(new NoUnit(1), ingredient);
        }

        String amount = ingredient.substring(0, index).trim();
        String name = ingredient.substring(index).trim();
        // todo assumes the number is always first
        return new Ingredient<>(new NoUnit(Double.parseDouble(amount)), name);
    }

    @SuppressWarnings("unchecked")
    private static Quantity parse(String unit, double value) {
        return switch (unit) {
            case "g", "gram", "grams" -> new Mass(value, GRAM);
            case "kg", "kilogram", "kilograms" -> new Mass(value, KILOGRAM);
            case "ml", "millilitre", "millilitres" -> new Volume(value, MILLILITER);
            case "l", "litre", "litres" -> new Volume(value, LITER);
            case "tbsp", "tablespoon", "tablespoons" -> new Volume(value, TABLESPOON);
            case "tsp", "teaspoon", "teaspoons" -> new Volume(value, TEASPOON);
            default -> null;
        };
    }

    private static double parseFractions(String amount) throws NumberFormatException {
        String[] fraction = Normalizer.normalize(amount, Normalizer.Form.NFKD).split("\u2044");
        return (double) Integer.parseInt(fraction[0]) / Integer.parseInt(fraction[1]);
    }

    private static double amountStringToDouble(String amount) throws NumberFormatException {
        String numericRegex = "-?\\d+(\\.\\d+)?";
        if (amount.matches(numericRegex)) {
            return Double.parseDouble(amount);
        } else {
            return parseFractions(amount);
        }
    }
}
