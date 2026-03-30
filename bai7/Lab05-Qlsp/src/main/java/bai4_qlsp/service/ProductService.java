package bai4_qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import bai4_qlsp.model.Product;
import bai4_qlsp.repository.ProductRepository;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;

    public List<Product> getAll() { return productRepository.findAll(); }

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
}