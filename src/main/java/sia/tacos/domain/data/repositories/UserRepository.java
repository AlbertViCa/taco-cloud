package sia.tacos.domain.data.repositories;

import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
