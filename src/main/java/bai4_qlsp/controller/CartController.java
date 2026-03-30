package bai4_qlsp.controller;

import bai4_qlsp.model.CartItem;
import bai4_qlsp.model.Product;
import bai4_qlsp.service.CartService;
import bai4_qlsp.service.OrderService;
import bai4_qlsp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = cartService.getCart(session);
        long totalAmount = cartService.getTotalAmount(session);
        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") int productId,
                           @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                           HttpSession session) {
        Product product = productService.get(productId);
        if (product != null) {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setImage(product.getImage());
            item.setPrice(product.getPrice());
            item.setQuantity(quantity);
            cartService.addToCart(session, item);
        }
        return "redirect:/cart";
    }

    @GetMapping("/add/{productId}")
    public String quickAddToCart(@PathVariable("productId") int productId,
                                 @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Product product = productService.get(productId);
        if (product != null) {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setImage(product.getImage());
            item.setPrice(product.getPrice());
            item.setQuantity(quantity);
            cartService.addToCart(session, item);
            redirectAttributes.addFlashAttribute("successMessage", "Da them \"" + product.getName() + "\" vao gio hang!");
        }
        return "redirect:/products";
    }

    @PostMapping("/api/add/{productId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiAddToCart(@PathVariable("productId") int productId,
                                                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Product product = productService.get(productId);
        if (product != null) {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setImage(product.getImage());
            item.setPrice(product.getPrice());
            item.setQuantity(quantity);
            cartService.addToCart(session, item);
            
            response.put("success", true);
            response.put("productName", product.getName());
            response.put("cartCount", cartService.getCartItemCount(session));
            response.put("totalAmount", cartService.getTotalAmount(session));
            return ResponseEntity.ok(response);
        }
        response.put("success", false);
        response.put("message", "Sản phẩm không tồn tại");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/api/count")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> getCartCount(HttpSession session) {
        Map<String, Integer> response = new HashMap<>();
        response.put("count", cartService.getCartItemCount(session));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable("productId") int productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        cartService.updateQuantity(session, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") int productId,
                                 HttpSession session) {
        cartService.removeFromCart(session, productId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("customerName") String customerName,
                          @RequestParam("customerEmail") String customerEmail,
                          @RequestParam("customerPhone") String customerPhone,
                          @RequestParam("shippingAddress") String shippingAddress,
                          HttpSession session,
                          Model model) {
        List<CartItem> cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        
        var order = orderService.createOrder(customerName, customerEmail, 
                customerPhone, shippingAddress, cart);
        
        cartService.clearCart(session);
        model.addAttribute("order", order);
        return "cart/order-success";
    }
}
