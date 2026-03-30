package bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bai4_qlsp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}