package nhonnguyen.food.controller;

import jakarta.validation.Valid;
import nhonnguyen.food.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import nhonnguyen.food.entity.Product;
import nhonnguyen.food.services.CartService;
import nhonnguyen.food.services.CategoryService;
import nhonnguyen.food.services.ProductService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping("/purchase")


public class PurchaseController {


    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/delete/{id}")
    public String deletePurchases(@PathVariable("id") Long id) {
        purchaseService.deletePurchases(id);
        return "redirect:/purchases";
    }



}
