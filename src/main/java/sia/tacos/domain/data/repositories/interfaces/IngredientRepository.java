package sia.tacos.domain.data.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
