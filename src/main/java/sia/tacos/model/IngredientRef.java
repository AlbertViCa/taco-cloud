package sia.tacos.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * This class represents a link between an {@link Ingredient} and {@link Taco}.
 * */

@Data
@Table("Ingredient_Ref")
public class IngredientRef {

    private final String ingredient;

}
