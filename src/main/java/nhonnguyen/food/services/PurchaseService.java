package nhonnguyen.food.services;

import jakarta.transaction.Transactional;
import nhonnguyen.food.entity.Order;
import nhonnguyen.food.entity.Purchase;
import nhonnguyen.food.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserService userService;  // UserService là một service để thao tác với thông tin người dùng
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;
    public void savePurchase(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            String username = userService.getUsernameByOrderId(orderId);
            double totalPrice = order.getTotalPrice();

            // Kiểm tra xem username và totalPrice có giá trị không null trước khi lưu
            if (username != null && totalPrice > 0) {
                Purchase purchase = new Purchase();
                purchase.setUsername(username);
                purchase.setTotalPrice(totalPrice);

                purchaseRepository.save(purchase);

                orderItemService.deleteOrderItemsByOrderId(orderId);
                orderService.deleteOrderById(orderId);
            }
        }
    }
    @Transactional
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public void deletePurchases(Long id){
        purchaseRepository.deleteById(id);
    }

}
