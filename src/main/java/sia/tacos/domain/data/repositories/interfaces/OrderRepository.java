package sia.tacos.domain.data.repositories.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.TacoOrder;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> getAllByDeliveryCityAndPlacedAtBetween(String deliveryCity, Date startDate, Date endDate);

    List<TacoOrder> getOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    @Query(value = "SELECT o FROM TacoOrder o WHERE o.deliveryCity = 'Seattle'")
    List<TacoOrder> readOrdersDeliveredInSeattle();
}
