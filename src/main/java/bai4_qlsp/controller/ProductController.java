package bai4_qlsp.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import bai4_qlsp.model.CartItem;
import bai4_qlsp.model.Product;
import bai4_qlsp.service.CartService;
import bai4_qlsp.service.CategoryService;
import bai4_qlsp.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired 
    private ProductService productService;
    @Autowired 
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String Index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            Model model) {
        
        Page<Product> productPage = productService.searchAndFilter(keyword, categoryId, page, size, sortBy, sortDir);
        
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("categories", categoryService.getAll());
        
        return "product/products";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String Create(Model model) {
        Product p = new Product();
        p.setCategory(new bai4_qlsp.model.Category());
        model.addAttribute("product", p);
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String Edit(@PathVariable("id") int id, Model model) {
        Product product = productService.get(id);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String Delete(@PathVariable("id") int id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
