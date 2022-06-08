package sia.tacos.domain.data.repositories.interfaces;

import sia.tacos.model.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
