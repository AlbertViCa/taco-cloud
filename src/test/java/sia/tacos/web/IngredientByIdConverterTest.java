package sia.tacos.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sia.tacos.domain.data.repositories.IngredientRepository;
import sia.tacos.model.Ingredient;
import sia.tacos.model.Ingredient.Type;
import sia.tacos.web.converters.IngredientByIdConverter;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientByIdConverterTest {

  private IngredientByIdConverter converter;

  @BeforeEach
  public void setup() {
    IngredientRepository ingredientRepo = mock(IngredientRepository.class);
    when(ingredientRepo.findById("AAAA"))
        .thenReturn(Optional.of(Ingredient.builder().id("AAAA").name("TEST INGREDIENT").type(Type.CHEESE).build()));
    when(ingredientRepo.findById("ZZZZ"))
        .thenReturn(Optional.empty());
    
    this.converter = new IngredientByIdConverter(ingredientRepo);
  }
  
  @Test
  public void shouldReturnValueWhenPresent() {
    assertThat(converter.convert("AAAA"))
        .isEqualTo(Ingredient.builder().id("AAAA").name("TEST INGREDIENT").type(Type.CHEESE).build());
  }

  @Test
  public void shouldReturnNullWhenMissing() {
    assertThat(converter.convert("ZZZZ")).isNull();
  }

}
