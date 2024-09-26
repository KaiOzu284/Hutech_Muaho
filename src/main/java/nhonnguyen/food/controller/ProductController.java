package nhonnguyen.food.controller;

import jakarta.validation.Valid;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;
    @GetMapping
    public String showAllProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "product/list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProductForm(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("image") MultipartFile multipartFile) {
        productService.addProduct(product, multipartFile);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(
            @PathVariable("id") Long productId,
            @ModelAttribute("product") @Valid Product updatedProduct,
            BindingResult bindingResult,
            @RequestParam(value = "image", required = false) MultipartFile multipartFile
    ) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return "product/edit";
        }

        Product product = productService.getProductById(productId);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setCostPrice(updatedProduct.getCostPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setCategory(updatedProduct.getCategory());

        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String imageBase64 = Base64.getEncoder().encodeToString(multipartFile.getBytes());
                product.setImage(imageBase64);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception, such as logging an error or showing an error message
            }
        }

        productService.updateProduct(product);
        return "redirect:/products";
    }




}
