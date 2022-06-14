package sia.tacos.domain.data.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.TacoOrder;
import sia.tacos.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    Optional<List<TacoOrder>> findByDeliveryZip(String deliveryZip);

    Optional<List<TacoOrder>> getAllByDeliveryCityAndPlacedAtBetween(String deliveryCity, Date startDate, Date endDate);

    Optional<List<TacoOrder>> getOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    @Query(value = "SELECT o FROM TacoOrder o WHERE o.deliveryCity = 'Seattle'")
    List<TacoOrder> readOrdersDeliveredInSeattle();

    Optional<List<TacoOrder>> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
