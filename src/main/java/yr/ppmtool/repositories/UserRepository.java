package yr.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yr.ppmtool.domain.User;

//loads user by username return UserDetail object
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

//    if using User findById(Long id): it returns Optional<User>
    User getById(Long id);

}
