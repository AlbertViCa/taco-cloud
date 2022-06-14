package sia.tacos.web.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.tacos.domain.data.repositories.IngredientRepository;
import sia.tacos.domain.data.repositories.UserRepository;
import sia.tacos.model.Ingredient;
import sia.tacos.model.Ingredient.Type;
import sia.tacos.model.User;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository ingredientRepository,
                                      UserRepository userRepo, PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
    return args -> {
      ingredientRepository.deleteAll();
      userRepo.deleteAll();

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

      userRepo.save(new User("alberto_vc", encoder.encode("123"),
          "Alberto Villalpando Cardona", "José Ma. Morelos #58", "Guadalupe", "Zacatecas",
          "98630", "4922023265"));
    };
  }
  
}
