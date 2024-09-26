package nhonnguyen.food.daos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();
    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getName(),
                        item.getName()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() +
                            item.getQuantity());
                    return true;
                })
                .orElse(false);
        if (!isExist) {
            cartItems.add(item);
        }
    }
    public void removeItems(Long Id) {
        cartItems.removeIf(product -> Objects.equals(product.getId(),
                Id));
    }
    public void updateItemQuantity(int itemId, int quantity) {
        for (Item item : cartItems) {
            if (item.getId() == itemId) {
                item.setQuantity(quantity);
                break;
            }
        }
    }


}
