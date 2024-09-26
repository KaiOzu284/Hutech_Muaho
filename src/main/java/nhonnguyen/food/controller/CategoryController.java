package nhonnguyen.food.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import nhonnguyen.food.entity.Category;
import nhonnguyen.food.services.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @ModelAttribute("errorMessage")
    public String errorMessage() {
        return "";
    }
    @GetMapping
    public String showAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("size",categories.size());
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        try {
            categoryService.deleteCategory(id);
            return "redirect:/categories";
        } catch (DataIntegrityViolationException ex) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("size", categories.size());
            model.addAttribute("categories", categories);
            model.addAttribute("errorMessage", "Không thể xóa category này vì category này được sử dụng trong một product trong hệ thống của bạn.");
            return "category/list";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        return "category/edit";
    }


    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long categoryId, @Valid @ModelAttribute("category") Category updatedCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/edit";
        }
        Category category = categoryService.getCategoryById(categoryId);
        category.setName(updatedCategory.getName());
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

}