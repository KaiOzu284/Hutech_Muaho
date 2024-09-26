package nhonnguyen.food.controller;
import nhonnguyen.food.entity.OrderItem;
import nhonnguyen.food.entity.Purchase;
import nhonnguyen.food.entity.User;
import nhonnguyen.food.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import nhonnguyen.food.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller

public class OrderController {
    @Autowired
    private OrderService orderService;
    private final OrderItemService orderItemService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private UserService userService;
    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }
    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "home/order";
    }
    @GetMapping("/order/details/{orderId}")
    public String showOrderDetails(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);

        if (order != null) {
            model.addAttribute("order", order);

            // Lấy danh sách OrderItem theo Order ID
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
            model.addAttribute("orderItems", orderItems);

            return "home/details";
        } else {
            // Xử lý trường hợp không tìm thấy Order
            return "redirect:/orders"; // Hoặc chuyển hướng đến trang danh sách đơn hàng
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestParam Long orderId) {
        try {
            // Lấy thông tin đơn hàng
            Order order = orderService.getOrderById(orderId);

            // Lấy thông tin người dùng từ đơn hàng
            User user = order.getUser();

            // Kiểm tra xem người dùng có địa chỉ email hay không
            if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
                // Gửi email tới người mua
                String recipientEmail = user.getEmail();
                String subject = "Xác nhận đơn hàng";
                String content = "<html>" +
                        "<body>" +
                        "<img class=\"hutech-logo\" src=\"https://media.loveitopcdn.com/3807/logo-hutech-1.png\" alt=\"Logo HUTECH\"> " +
                        "<h1>Đơn hàng của bạn đã được xác nhận bởi --> " + user.getUsername() + "</h1>" +
                        "<p>Đơn hàng của bạn đã được xác nhận và đang được chuẩn bị giao. Cảm ơn bạn đã mua hàng!</p>" +
                        "</body>" +
                        "</html>";

                emailService.sendEmail(recipientEmail, subject, content);

                return ResponseEntity.ok("Email đã được gửi tới người mua hàng.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể gửi email do địa chỉ email không tồn tại.");
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
            System.out.println(e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi xác nhận đơn hàng.");
        }
    }
    @PostMapping("/confirm/{orderId}")
    public String confirmOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            String username = userService.getUsernameByOrderId(orderId);
            double totalPrice = order.getTotalPrice();

            // Kiểm tra xem username và totalPrice có giá trị không null trước khi lưu
            if (username != null && totalPrice > 0) {
                purchaseService.savePurchase(orderId);
                return "redirect:/homes";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
    }
    @GetMapping("/purchases")
    public String showPurchasePage(Model model) {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        model.addAttribute("purchases", purchases);
        return "purchase/list";
    }
}