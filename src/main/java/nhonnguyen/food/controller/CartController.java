package nhonnguyen.food.controller;

import nhonnguyen.food.daos.Cart;
import nhonnguyen.food.daos.Item;
import nhonnguyen.food.entity.*;
import nhonnguyen.food.repository.IUserRepository;
import nhonnguyen.food.repository.OrderRepository;
import nhonnguyen.food.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nhonnguyen.food.services.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")

public class CartController {
    private final CartService cartService;

    @Autowired
    private final OrderService orderService;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;



    @Autowired
    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }
    @GetMapping
    public String showCart(HttpSession session,
                           @NotNull Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice",
                cartService.getSumPrice(session));
        model.addAttribute("totalQuantity",
                cartService.getSumQuantity(session));
        return "home/cart";
    }
    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session,
                                 @PathVariable Long id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart ";
    }
    @PostMapping("/updateCart")
    public String updateCart(HttpSession session,
                             @RequestParam("itemId") int itemId,
                             @RequestParam("quantity") int quantity
                             ) {
        Cart cart = cartService.getCart(session);
        cart.updateItemQuantity(itemId, quantity);


        cartService.updateCart(session, cart);



        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            Cart cart = cartService.getCart(session);

            List<OrderItem> orderItems = new ArrayList<>();
            for (Item cartItem : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setItemName(cartItem.getName());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItems.add(orderItem);
            }
            Order order = (Order) session.getAttribute("order");
            String address = order.getAddress();
            double totalPrice = cartService.getSumPrice(session);

            orderService.saveOrder(user, orderItems, totalPrice, address);
            cartService.removeCart(session);

            return "cart/sucess";
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi ra log hoặc console
            return "redirect:/error";
        }
    }

    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam("address") String address) {

        return "redirect:/success"; // Hoặc trả về view thành công khác
    }

    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        // Lấy thông tin đơn hàng từ service
        Order order = orderService.findById(orderId);

        // Kiểm tra xem đơn hàng có tồn tại hay không
        if (order == null) {
            // Xử lý trường hợp không tìm thấy đơn hàng, ví dụ: đưa ra thông báo lỗi
            return "redirect:/error";
        }

        // Đưa dữ liệu vào model để hiển thị trong view
        model.addAttribute("order", order);

        // Trả về tên view (order.html)
        return "order";
    }
    @GetMapping("/enterAddress")
    public String enterAddressForm() {
        return "home/address"; // Thay bằng tên template thích hợp
    }
    @PostMapping("/processAddress")
    public String processAddress(@RequestParam("address") String address, HttpSession session) {
        // Tạo một đơn hàng mới
        Order order = new Order();

        // Lưu địa chỉ vào đơn hàng
        order.setAddress(address);

        // Lưu đơn hàng vào session (để có thể truy cập từ các phương thức khác)
        session.setAttribute("order", order);

        // Chuyển hướng đến trang nhập địa chỉ
        return "redirect:/cart/checkout";
    }



}