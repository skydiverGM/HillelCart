package imaks.hillelcart.bean;


import imaks.hillelcart.entity.Product;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Data
public class Cart {
    private final List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        if (product != null) {
            items.add(product);
        } else {
            System.out.println("Товар не знайдено.");
        }
    }

    public void removeProductById(Long productId) {
        boolean removed = items.removeIf(p -> p.getId().equals(productId));
        if (!removed) {
            System.out.println("Товар з id " + productId + " не знайдено в кошику.");
        }
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Кошик порожній.");
        } else {
            System.out.println("Вміст кошика:");
            items.forEach(System.out::println);
        }
    }
}
