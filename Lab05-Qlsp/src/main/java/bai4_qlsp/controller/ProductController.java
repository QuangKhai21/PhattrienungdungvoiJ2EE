package bai4_qlsp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import bai4_qlsp.model.Product;
import bai4_qlsp.service.CategoryService;
import bai4_qlsp.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        Product p = new Product();
        p.setCategory(new bai4_qlsp.model.Category()); // Khởi tạo để tránh lỗi null ở giao diện
        model.addAttribute("product", p);
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable("id") int id, Model model) {
        Product product = productService.get(id);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }
    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id") int id) {
        productService.delete(id);
        return "redirect:/products";
    }
    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct, BindingResult result,
                       @RequestParam("imageProduct") MultipartFile imageProduct, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        productService.updateImage(editProduct, imageProduct);
        productService.update(editProduct);
        return "redirect:/products";
    }

    @PostMapping("/create")
    public String Create(@Valid Product newProduct, BindingResult result,
                         @RequestParam("imageProduct") MultipartFile imageProduct, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        productService.updateImage(newProduct, imageProduct);
        productService.add(newProduct);
        return "redirect:/products";
    }
}