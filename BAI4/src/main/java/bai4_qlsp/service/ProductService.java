package bai4_qlsp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import bai4_qlsp.model.Product;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() { return listProduct; }

    public Product get(int id) {
        return listProduct.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream().mapToInt(Product::getId).max().orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }
    public void delete(int id) {
        listProduct.removeIf(p -> p.getId() == id);
    }
    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            if (editProduct.getImage() != null) find.setImage(editProduct.getImage());
        }
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