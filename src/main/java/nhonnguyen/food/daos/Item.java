package nhonnguyen.food.daos;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;
    private String Name;
    private Double costPrice;
    private int quantity;



    public void setTotalPrice(double v) {
    }
}