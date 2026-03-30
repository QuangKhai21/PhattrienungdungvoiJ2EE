package bai4_qlsp.service;

import bai4_qlsp.model.Product;
import bai4_qlsp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {
    @Autowired 
    private ProductRepository productRepository;

    public List<Product> getAll() { 
        return productRepository.findAll(); 
    }

    public Product get(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void add(Product newProduct) {
        productRepository.save(newProduct);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public void update(Product editProduct) {
        productRepository.save(editProduct);
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("target/classes/static/images");
                if (!Files.exists(dirImages)) Files.createDirectories(dirImages);
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public Page<Product> getProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.searchByName(keyword, pageable);
    }

    public Page<Product> filterByCategory(int categoryId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<Product> searchAndFilter(String keyword, Integer categoryId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        if (keyword != null && !keyword.trim().isEmpty() && categoryId != null && categoryId > 0) {
            return productRepository.searchByNameAndCategory(keyword, categoryId, pageable);
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            return productRepository.searchByName(keyword, pageable);
        } else if (categoryId != null && categoryId > 0) {
            return productRepository.findByCategoryId(categoryId, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }
}
