package id.bti.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import id.bti.warehouse.entity.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, String> {

}
