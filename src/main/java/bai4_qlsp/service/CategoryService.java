package bai4_qlsp.service;

import bai4_qlsp.model.Category;
import bai4_qlsp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category get(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}