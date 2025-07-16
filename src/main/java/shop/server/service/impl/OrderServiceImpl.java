package shop.server.service.impl;

import com.project.shop_dior.dtos.CartItemDTO;
import com.project.shop_dior.dtos.OrderDTO;
import com.project.shop_dior.exception.DataNotFoundException;
import com.project.shop_dior.models.*;
import com.project.shop_dior.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final ProductDetailRepository productDetailRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find user with id: "+orderDTO.getUserId()));
        //converse orderDTO =>Order
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper ->mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO,order);
        order.setUser(user);
        order.setOrderDate(new Date());// lay thoi gian hien tai
        order.setStatus(OrderStatus.PENDING);
        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now():orderDTO.getShippingDate();
        if (shippingDate.isBefore( LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalMoney(orderDTO.getTotalMoney());
        if(orderDTO.getShippingAddress() == null) {
            order.setShippingAddress(orderDTO.getAddress());
        }

        // tao danh sach cac doi tuong tu OrderDetail tu cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItem()){
            // tao 1 doi tuong OrderDetail tu CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            //lay thong tin cu cartItemDTO
            Long productId = cartItemDTO.getProductDetailId();
            int quantity = cartItemDTO.getQuantity();
            //tim thong tin san pham tu csdl
            ProductDetail product = productDetailRepository.findById(productId)
                    .orElseThrow(()->new DataNotFoundException("Product not found with id: "+productId));
            //Dat thong tin cho OrderDetail
            orderDetail.setProductDetail(product);
            orderDetail.setNumberOfProducts(quantity);
            //Cac truong khac cua orderDetail neu can
//            orderDetail.setPrice(product.getProduct().getPrice());
            //Them orderDetail vao danh sach
            orderDetails.add(orderDetail);
        }

        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return order;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Cannot find order with id: "+id));
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(()->
                new DataNotFoundException("Cannot find user with id: "+id));
        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper->mapper.skip(Order::setId));
        modelMapper.map(orderDTO,order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void deleteOrder(Long id) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElse(null);
        if(order!=null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
                 return orderRepository.findByKeyword(keyword, pageable);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, String status) throws DataNotFoundException {
        Order order = getOrderById(id); // Sẽ tìm theo ID trước, sau đó tìm theo vnpTxnRef

        // Kiểm tra trạng thái hợp lệ
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        // Kiểm tra xem trạng thái có nằm trong danh sách hợp lệ không
        if (!OrderStatus.VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        // Kiểm tra logic chuyển đổi trạng thái
        String currentStatus = order.getStatus();
        if (currentStatus.equals(OrderStatus.DELIVERED) && !status.equals(OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("Không thể chuyển trạng thái từ DELIVERED sang " + status);
        }

        if (currentStatus.equals(OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("Không thể thay đổi trạng thái đơn đã CANCELLED");
        }

        if (status.equals(OrderStatus.CANCELLED)) {
            // Kiểm tra xem đơn hàng có thể bị hủy không
            if (!currentStatus.equals(OrderStatus.PENDING)) {
                throw new IllegalArgumentException("Order chỉ có thể bị hủy khi ở trạng thái PENDING ");
            }
        }
        //  Kiểm tra tồn kho khi chuyển sang PROCESSING
        if (status.equals(OrderStatus.PROCESSING)) {
            for (OrderDetail detail : order.getOrderDetails()) {
                ProductDetail productDetail = detail.getProductDetail();
                int currentQty = productDetail.getQuantity();
                int orderedQty = detail.getNumberOfProducts();

                if (currentQty < orderedQty) {
                    throw new IllegalArgumentException(
                            "Sản phẩm " + productDetail.getProduct().getName() + " không đủ số lượng tồn kho");
                }
            }
        }
        if (status.equals(OrderStatus.DELIVERED)) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                ProductDetail productDetail = orderDetail.getProductDetail();
                int currentQty = productDetail.getQuantity();
                int orderedQty = orderDetail.getNumberOfProducts();

                if (currentQty < orderedQty) {
                    throw new IllegalArgumentException(
                            "Sản phẩm " + productDetail.getProduct().getName() + " không đủ tồn kho để giao hàng");
                }

                productDetail.setQuantity(currentQty - orderedQty);
                productDetailRepository.save(productDetail);
            }
        }
        // Cập nhật trạng thái đơn hàng
        order.setStatus(status);
        // Lưu đơn hàng đã cập nhật
        return orderRepository.save(order);
    }
}
