package nhonnguyen.food.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nhonnguyen.food.entity.Order;
import nhonnguyen.food.entity.OrderItem;
import nhonnguyen.food.entity.User;
import nhonnguyen.food.repository.OrderItemRepository;
import nhonnguyen.food.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    @Autowired OrderItemService orderItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository,OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;

    }

    @Transactional
    public void saveOrder(User user, List<OrderItem> orderItems, double totalPrice, String address) {
        try {
            if (user == null || orderItems == null || orderItems.isEmpty()) {
                // Xử lý trường hợp dữ liệu đầu vào không hợp lệ
                throw new IllegalArgumentException("Invalid input data for saving order.");
            }


            Order order = new Order();
            order.setUser(user);
            order.setOrderItems(orderItems);

            order.setAddress(address);

            order.setTotalPrice(totalPrice);


            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
            }

            orderRepository.save(order);
        } catch (Exception e) {
            // In lỗi ra log hoặc console để kiểm tra
            e.printStackTrace();
            throw e; // Ném lại exception để có thể xử lý ở lớp gọi
        }
    }
    public Order findById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        // Kiểm tra xem đơn hàng có tồn tại hay không
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            // Xử lý trường hợp không tìm thấy đơn hàng, có thể ném một exception hoặc trả về null tùy thuộc vào yêu cầu của bạn.
            return optionalOrder.orElse(null);
            // Hoặc có thể trả về null nếu bạn muốn xử lý nó trong controller
            // return null;
        }
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }
    public void deleteOrderById(Long orderId) {
        // Xóa chi tiết đơn hàng trước
        orderItemService.deleteOrderItemsByOrderId(orderId);

        // Sau đó, xóa đơn hàng
        // Thực hiện xóa trực tiếp mà không sử dụng repository
        // Điều này chỉ là một ví dụ, thực tế có thể cần kiểm tra và xử lý nếu cần
        // Ví dụ: kiểm tra xem đơn hàng có tồn tại không trước khi xóa
        orderRepository.deleteById(orderId);
    }

}