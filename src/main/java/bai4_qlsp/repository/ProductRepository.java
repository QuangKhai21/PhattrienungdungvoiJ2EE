package bai4_qlsp.repository;

import bai4_qlsp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    Page<Product> searchByName(@Param("keyword") String keyword, Pageable pageable);

    Page<Product> findByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.name LIKE %:keyword%")
    Page<Product> searchByNameAndCategory(@Param("keyword") String keyword, @Param("categoryId") int categoryId, Pageable pageable);
}
