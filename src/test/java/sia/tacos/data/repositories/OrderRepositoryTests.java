package sia.tacos.data.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sia.tacos.domain.data.repositories.OrderRepository;
import sia.tacos.model.Ingredient;
import sia.tacos.model.Ingredient.Type;
import sia.tacos.model.Taco;
import sia.tacos.model.TacoOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTests {

  @Autowired
  OrderRepository orderRepo;
  
  @Test
  public void saveOrderWithTwoTacos() {
    TacoOrder order = new TacoOrder();
    order.setDeliveryName("Test McTest");
    order.setDeliveryStreet("1234 Test Lane");
    order.setDeliveryCity("Testville");
    order.setDeliveryState("CO");
    order.setDeliveryZip("80123");
    order.setCcNumber("4111111111111111");
    order.setCcExpiration("10/23");
    order.setCcCVV("123");

    Taco taco1 = new Taco();
    taco1.setName("Taco One");
    taco1.addIngredient(Ingredient.builder().id("FLTO").name("Flour Tortilla").type(Type.WRAP).build());
    taco1.addIngredient(Ingredient.builder().id("GRBF").name("Ground Beef").type(Type.PROTEIN).build());
    taco1.addIngredient(Ingredient.builder().id("CHED").name("Cheddar").type(Type.CHEESE).build());
    order.addTaco(taco1);

    Taco taco2 = new Taco();
    taco2.setName("Taco Two");
    taco2.addIngredient(Ingredient.builder().id("COTO").name("Corn Tortilla").type(Type.WRAP).build());
    taco2.addIngredient(Ingredient.builder().id("CARN").name("Carnitas").type(Type.PROTEIN).build());
    taco2.addIngredient(Ingredient.builder().id("JACK").name("Monterrey Jack").type(Type.CHEESE).build());
    order.addTaco(taco2);
    
    TacoOrder savedOrder = orderRepo.save(order);
    assertThat(savedOrder.getId()).isNotNull();
        
    TacoOrder fetchedOrder = orderRepo.findById(savedOrder.getId()).isPresent() ? orderRepo.findById(savedOrder.getId()).get() : null;

    assert fetchedOrder != null;

    assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
    assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
    assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
    assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
    assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
    assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
    assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
    assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
    assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());

    List<Taco> tacos = fetchedOrder.getTacos();
    assertThat(tacos.size()).isEqualTo(2);
    assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2);
  }
  
}
