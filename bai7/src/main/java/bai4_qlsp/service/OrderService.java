package bai4_qlsp.service;

import bai4_qlsp.model.CartItem;
import bai4_qlsp.model.Order;
import bai4_qlsp.model.OrderDetail;
import bai4_qlsp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(String customerName, String customerEmail, 
                            String customerPhone, String shippingAddress,
                            List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(new Date());

        long totalAmount = 0;
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProductId(item.getProductId());
            detail.setProductName(item.getName());
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            order.getOrderDetails().add(detail);
            totalAmount += item.getTotalPrice();
        }
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(int id) {
        return orderRepository.findById(id).orElse(null);
    }
}
