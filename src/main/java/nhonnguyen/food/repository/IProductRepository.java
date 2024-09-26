package nhonnguyen.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nhonnguyen.food.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
