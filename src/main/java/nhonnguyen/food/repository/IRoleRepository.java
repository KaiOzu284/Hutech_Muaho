package nhonnguyen.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import nhonnguyen.food.entity.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleName);
    @Query("SELECT r.id FROM Role r WHERE r.name = ?1")
    Long getRoleIdByName(String roleName);

}
