package shop.server.service;

import com.project.shop_dior.dtos.OrderDTO;
import com.project.shop_dior.exception.DataNotFoundException;
import com.project.shop_dior.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    Order getOrder(Long id);
    Order getOrderById(Long orderId);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    List<Order> findByUserId(Long userId);
    void deleteOrder(Long id) throws DataNotFoundException;
    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);
    Order updateOrderStatus(Long id, String status) throws DataNotFoundException;
}
