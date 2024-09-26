package nhonnguyen.food.controller;

import jakarta.servlet.http.HttpSession;
import nhonnguyen.food.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import nhonnguyen.food.daos.Item;
import nhonnguyen.food.entity.Product;
import nhonnguyen.food.services.CartService;
import nhonnguyen.food.services.ProductService;


import java.util.List;


@Controller
@RequestMapping("/homes")
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @GetMapping
    public String home(Model model){

        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "home/index";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String name,
                            @RequestParam double costPrice,
                            @RequestParam(defaultValue = "1") int quantity,
                            @RequestParam String productImage
                           ) {

        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name, costPrice, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/homes";
    }

}
