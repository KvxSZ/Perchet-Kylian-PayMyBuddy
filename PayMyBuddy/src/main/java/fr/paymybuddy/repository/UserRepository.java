package fr.paymybuddy.repository;

import fr.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // Find user by email
    public Optional<User> findByEmail(String email);
}
