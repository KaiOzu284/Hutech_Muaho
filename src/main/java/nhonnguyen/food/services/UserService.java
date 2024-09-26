package nhonnguyen.food.services;

import nhonnguyen.food.entity.Order;
import nhonnguyen.food.repository.IRoleRepository;
import nhonnguyen.food.repository.IUserRepository;
import nhonnguyen.food.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nhonnguyen.food.entity.User;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user) {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUserName(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (roleId != null && userId != null) {
            userRepository.addRoleToUser(userId, roleId);
        }
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public User getUserById(Long id) {
        // Assuming you have a method in your repository to get a user by ID
        return userRepository.findById(id).orElse(null);
    }
    public String getUsernameByOrderId(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            User user = orderOptional.get().getUser();
            return user != null ? user.getUsername() : null;
        } else {
            return null;
        }
    }
}