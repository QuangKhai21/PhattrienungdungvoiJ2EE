package bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bai4_qlsp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}