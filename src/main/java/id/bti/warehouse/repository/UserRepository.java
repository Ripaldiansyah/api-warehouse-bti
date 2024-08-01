package id.bti.warehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.bti.warehouse.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String name);
}
