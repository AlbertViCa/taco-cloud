package sia.tacos.model;

import lombok.Data;

/**
 * This class represents a link between an {@link Ingredient} and {@link Taco}.
 * */

@Data
public class IngredientRef {

    private final String ingredient;

}
