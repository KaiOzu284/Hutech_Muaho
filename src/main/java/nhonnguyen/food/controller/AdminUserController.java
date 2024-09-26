package nhonnguyen.food.controller;

import nhonnguyen.food.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import nhonnguyen.food.entity.User;
import nhonnguyen.food.services.AdminUserService;
import nhonnguyen.food.services.RoleService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private RoleService roleService; // Add this autowired variable

    @GetMapping
    public String showAllCategories(Model model) {
        List<User> users = adminUserService.getAllUser();
        model.addAttribute("users",users);
        return "user/list";
    }
//    @GetMapping("/edit/{id}")
//    public String showEditUserRolePage(@PathVariable("id") Long userId, Model model) {
//        User user = adminUserService.getUserById(userId);
//        List<Role> roles = roleRepository.findAll(); // Retrieve all roles
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roles);
//        return "user/edit";
//    }
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long userId, Model model) {
        User user = adminUserService.getUserById(userId);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long userId, @ModelAttribute("user") User updatedUser) {
        User user = adminUserService.getUserById(userId);
        user.setUsername(updatedUser.getUsername());
//        user.setPassword(new
//                BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setRoles(updatedUser.getRoles());
        adminUserService.updateUser(user);
        return "redirect:/users";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        adminUserService.deleteUser(id);
        return "redirect:/users";
    }

}
