package id.bti.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import id.bti.warehouse.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
