package sia.tacos.domain.data.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
