package nhonnguyen.food.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nhonnguyen.food.Validator.annotation.ValidCategoryId;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String description;
    private double costPrice;
    private double salePrice;

    private int quantity;


    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    private String completeImage; // New field to store the complete base64 image string

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;

    private boolean is_deleted;
    private boolean is_activated;
}
