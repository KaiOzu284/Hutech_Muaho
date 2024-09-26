package nhonnguyen.food.services;

import nhonnguyen.food.repository.IAdminUserCategory;
import nhonnguyen.food.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nhonnguyen.food.entity.Role;
import nhonnguyen.food.entity.User;

import java.util.List;
import java.util.Optional;
@Service

public class AdminUserService {
    @Autowired
    private IAdminUserCategory adminUserCategory;
    @Autowired
    private IRoleRepository roleRepository;

    public List<User> getAllUser() {
        return adminUserCategory.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public User getUserById(Long id) {
        return adminUserCategory.findById(id).orElse(null);
    }

    public void addUser(User user) {
        adminUserCategory.save(user);
    }

    public void deleteUser(Long id) {
        adminUserCategory.deleteById(id);
    }

    public void updateUser(User user) {
        adminUserCategory.save(user);
    }

    public void updateUserRole(Long userId, String roleName) {
        User user = adminUserCategory.findById(userId).orElse(null);
        if (user != null) {
            // Find the previous role by name
            Role previousRole = roleRepository.findByName(roleName);
            if (previousRole != null) {
                // Remove the previous role
                user.getRoles().remove(previousRole);
            }

            // Find the new role by name
            Role newRole = roleRepository.findByName(roleName);
            if (newRole != null) {
                // Add the new role
                user.getRoles().add(newRole);

                // Save the updated user
                adminUserCategory.save(user);
            }
        }
    }
}
