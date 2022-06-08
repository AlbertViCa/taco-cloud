package sia.tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sia.tacos.domain.data.repositories.interfaces.IngredientRepository;
import sia.tacos.model.Ingredient;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository) {
        return args -> {
            ingredientRepository.save(Ingredient.builder().id("FLTO").name("Flour Tortilla").type(Ingredient.Type.WRAP).build());
            ingredientRepository.save(Ingredient.builder().id("COTO").name("Corn Tortilla").type(Ingredient.Type.WRAP).build());
            ingredientRepository.save(Ingredient.builder().id("GRBF").name("Ground Beef").type(Ingredient.Type.PROTEIN).build());
            ingredientRepository.save(Ingredient.builder().id("CARN").name("Carnitas").type(Ingredient.Type.PROTEIN).build());
            ingredientRepository.save(Ingredient.builder().id("TMTO").name("Diced Tomatoes").type(Ingredient.Type.VEGGIES).build());
            ingredientRepository.save(Ingredient.builder().id("LETC").name("Lettuce").type(Ingredient.Type.VEGGIES).build());
            ingredientRepository.save(Ingredient.builder().id("CHED").name("Cheddar").type(Ingredient.Type.CHEESE).build());
            ingredientRepository.save(Ingredient.builder().id("JACK").name("Monterrey Jack").type(Ingredient.Type.CHEESE).build());
            ingredientRepository.save(Ingredient.builder().id("SLSA").name("Salsa").type(Ingredient.Type.SAUCE).build());
            ingredientRepository.save(Ingredient.builder().id("SRCR").name("Sour Cream").type(Ingredient.Type.SAUCE).build());
        };
    }

}
