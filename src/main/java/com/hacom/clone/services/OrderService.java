package com.hacom.clone.services;

import com.hacom.clone.entities.Order;
import com.hacom.clone.entities.OrderItem;
import com.hacom.clone.entities.Product;
import com.hacom.clone.repositories.OrderRepository;
import com.hacom.clone.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Order orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setCustomerAddress(orderRequest.getCustomerAddress());
        order.setCustomerPhone(orderRequest.getCustomerPhone());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        double total = 0;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItem itemRequest : orderRequest.getOrderItems()) {
            // 1. Kiểm tra sản phẩm có tồn tại không
            Product product = productRepository.findById(itemRequest.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm ID " + itemRequest.getProduct().getId() + " không tồn tại!"));

            // 2. Kiểm tra số lượng tồn kho
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng!");
            }

            // 3. Trừ số lượng tồn kho
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            // 4. Tạo chi tiết đơn hàng
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice()); // Lấy giá hiện tại của sản phẩm
            
            total += item.getPrice() * item.getQuantity();
            items.add(item);
        }

        order.setOrderItems(items);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + id));
    }
}