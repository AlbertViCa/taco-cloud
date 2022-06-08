package sia.tacos.web.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import sia.tacos.domain.data.repositories.interfaces.IngredientRepository;
import sia.tacos.model.Ingredient;
import sia.tacos.model.Ingredient.Type;

public class DataLoader {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository) {
        return args -> {
            ingredientRepository.save(Ingredient.builder().id("FLTO").name("Flour Tortilla").type(Type.WRAP).build());
            ingredientRepository.save(Ingredient.builder().id("COTO").name("Corn Tortilla").type(Type.WRAP).build());
            ingredientRepository.save(Ingredient.builder().id("GRBF").name("Ground Beef").type(Type.PROTEIN).build());
            ingredientRepository.save(Ingredient.builder().id("CARN").name("Carnitas").type(Type.PROTEIN).build());
            ingredientRepository.save(Ingredient.builder().id("TMTO").name("Diced Tomatoes").type(Type.VEGGIES).build());
            ingredientRepository.save(Ingredient.builder().id("LETC").name("Lettuce").type(Type.VEGGIES).build());
            ingredientRepository.save(Ingredient.builder().id("CHED").name("Cheddar").type(Type.CHEESE).build());
            ingredientRepository.save(Ingredient.builder().id("JACK").name("Monterrey Jack").type(Type.CHEESE).build());
            ingredientRepository.save(Ingredient.builder().id("SLSA").name("Salsa").type(Type.SAUCE).build());
            ingredientRepository.save(Ingredient.builder().id("SRCR").name("Sour Cream").type(Type.SAUCE).build());
        };
    }
}
