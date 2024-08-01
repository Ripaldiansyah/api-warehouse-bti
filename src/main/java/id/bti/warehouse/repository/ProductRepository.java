package id.bti.warehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.bti.warehouse.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

    public Optional<Product> findByName(String name);
}
