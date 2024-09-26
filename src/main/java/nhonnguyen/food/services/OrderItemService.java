package nhonnguyen.food.services;

import nhonnguyen.food.entity.OrderItem;
import nhonnguyen.food.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // Các phương thức khác...

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
    public Optional<OrderItem> getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }
    public void deleteOrderItemsByOrderId(Long orderId) {
        // Thực hiện xóa chi tiết đơn hàng trực tiếp mà không sử dụng repository
        // Điều này chỉ là một ví dụ, thực tế có thể cần kiểm tra và xử lý nếu cần
        // Ví dụ: kiểm tra xem chi tiết đơn hàng có tồn tại không trước khi xóa
        // Thực hiện xóa chi tiết đơn hàng
    }
}

