package sia.tacos.domain.data.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.Taco;
import sia.tacos.model.TacoOrder;

public interface OrderRepository extends CrudRepository<Taco, Long> {
    TacoOrder save(TacoOrder order);
}
