package nhonnguyen.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nhonnguyen.food.entity.User;

public interface IAdminUserCategory extends JpaRepository<User, Long> {
}
