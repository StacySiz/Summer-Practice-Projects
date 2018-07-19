package ru.itis.mq.app.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.mq.app.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {

}
