package imaks.hillelcart.repo;

import imaks.hillelcart.entity.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        products.add(new Product(1L, "Apple", 1.0));
        products.add(new Product(2L, "Banana", 0.5));
        products.add(new Product(3L, "Orange", 0.8));
    }

    public List<Product> findAll() {
        return products;
    }

    public Product findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
}
