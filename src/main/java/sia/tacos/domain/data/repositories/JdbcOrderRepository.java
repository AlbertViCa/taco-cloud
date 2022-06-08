package sia.tacos.domain.data.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sia.tacos.domain.data.repositories.interfaces.OrderRepository;
import sia.tacos.model.IngredientRef;
import sia.tacos.model.Taco;
import sia.tacos.model.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * <p>
     * First, you create a <b>PreparedStatementCreatorFactory</b> that describes the insert query
     * along with the types of the query’s input fields. Because you’ll later need to fetch the saved order’s ID,
     * you also will need to call <b>setReturnGeneratedKeys(true)</b>.
     * </p>
     * <p>
     * After defining the <b>PreparedStatementCreatorFactory</b>, you use it to create a <b>PreparedStatementCreator</b>,
     * passing in the values from the <b>TacoOrder</b> object that will be persisted.
     * The last field given to the <b>PreparedStatementCreator</b> is the date that the order is created,
     * which you’ll also need to set on the <b>TacoOrder</b> object itself so that the returned <b>TacoOrder</b> will have that information available.
     * </p>
     * <p>
     * Now that you have a <b>PreparedStatementCreator</b> in hand, you’re ready to actually save the order data by
     * calling the <b>update()</b> method on JdbcTemplate, passing in the <b>PreparedStatementCreator</b> and a <b>GeneratedKeyHolder</b>.
     * After the order data has been saved, the <b>GeneratedKeyHolder</b> will contain the value of the id field as assigned by
     * the database and should be copied into the <b>TacoOrder</b> object’s id property.
     * </p>
     * */

    @Override
    @Transactional
    public TacoOrder save(@NotNull TacoOrder order) {
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco_Order "
                        + "(delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, "
                        + "cc_expiration, cc_cvv, placed_at) "
                        + "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()
                        )
                );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        Long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i = 0;

        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    private void saveTaco(Long orderId, int orderKey, @NotNull Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco_Order_Tacos "
                        + "(taco_order_id, taco_order_key, taco_id, created_at) "
                        + "values (?,?,?,?)",
                        Types.BIGINT, Types.INTEGER, Types.BIGINT, Types.TIMESTAMP
                );
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                orderId,
                                orderKey,
                                taco.getId(),
                                taco.getCreatedAt()
                        )
                );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        Long tacoId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        taco.setId(tacoId);

        saveIngredients(tacoId, taco.getIngredients());
    }

    private void saveIngredients(Long tacoId, @NotNull List<IngredientRef> ingredientsRef) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientsRef) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                            + "values (?, ?, ?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }
    }
}
