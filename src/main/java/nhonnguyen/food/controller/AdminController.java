package nhonnguyen.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String home(){return "admin/index";}
    @GetMapping("/categories")
    public String categories(Model model){
        model.addAttribute("title", "Category");
        return "admin/nhasanxuat";
    }
}
