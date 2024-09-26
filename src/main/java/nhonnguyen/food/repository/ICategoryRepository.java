package nhonnguyen.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nhonnguyen.food.entity.Category;

@Repository

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
