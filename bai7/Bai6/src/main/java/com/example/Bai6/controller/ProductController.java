package com.example.bai5_tongvutanphat_2280602321.controller;

import com.example.bai5_tongvutanphat_2280602321.model.Product;
import com.example.bai5_tongvutanphat_2280602321.service.CategoryService;
import com.example.bai5_tongvutanphat_2280602321.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("categoryId") Integer categoryId,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        product.setCategory(categoryService.getCategoryById(categoryId));
        if (imageFile != null && !imageFile.isEmpty()) {
            String path = saveImageFile(imageFile);
            product.setImage(path);
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        Path uploadsDir = Paths.get("uploads").toAbsolutePath().normalize();
        if (!Files.exists(uploadsDir)) {
            Files.createDirectories(uploadsDir);
        }
        String ext = file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")
                ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
                : "";
        String filename = UUID.randomUUID().toString() + ext;
        Path target = uploadsDir.resolve(filename);
        file.transferTo(target.toFile());
        return "/uploads/" + filename;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @PostMapping("/edit/{id}")
    public String redirectEdit(@PathVariable("id") Long id) {
        return "redirect:/products/edit/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProductPost(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

