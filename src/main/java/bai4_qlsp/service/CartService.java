package bai4_qlsp.service;

import bai4_qlsp.model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    public static final String CART_SESSION_KEY = "cart";

    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, CartItem item) {
        List<CartItem> cart = getCart(session);
        boolean exists = false;
        for (CartItem ci : cart) {
            if (ci.getProductId() == item.getProductId()) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                exists = true;
                break;
            }
        }
        if (!exists) {
            cart.add(item);
        }
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(HttpSession session, int productId, int quantity) {
        List<CartItem> cart = getCart(session);
        for (CartItem ci : cart) {
            if (ci.getProductId() == productId) {
                if (quantity <= 0) {
                    cart.remove(ci);
                } else {
                    ci.setQuantity(quantity);
                }
                break;
            }
        }
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeFromCart(HttpSession session, int productId) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(ci -> ci.getProductId() == productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public long getTotalAmount(HttpSession session) {
        List<CartItem> cart = getCart(session);
        long total = 0;
        for (CartItem ci : cart) {
            total += ci.getTotalPrice();
        }
        return total;
    }

    public int getCartItemCount(HttpSession session) {
        List<CartItem> cart = getCart(session);
        int count = 0;
        for (CartItem ci : cart) {
            count += ci.getQuantity();
        }
        return count;
    }
}
