package ro.unitbv.springwebapp.service;

import org.springframework.stereotype.Service;
import ro.unitbv.springwebapp.dto.CreateProductRequest;
import ro.unitbv.springwebapp.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {
    private final Map<Integer, Product> products = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(4);

    public ProductService() {
        products.put(1, new Product(1, "Laptop", 3500.0, 10));
        products.put(2, new Product(2, "Mouse", 120.0, 50));
        products.put(3, new Product(3, "Keyboard", 250.0, 30));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findById(Integer id) {
        return products.get(id);
    }

    public Product create(CreateProductRequest request) {
        Integer id = nextId.getAndIncrement();
        Product product = new Product(id, request.getName(), request.getPrice(), request.getStock());
        products.put(id, product);
        return product;
    }

    public Product update(Integer id, Product product) {
        if (!products.containsKey(id)) return null;
        product.setId(id);
        products.put(id, product);
        return product;
    }

    public boolean deleteById(Integer id) {
        return products.remove(id) != null;
    }

    public int productsCount(){
        return products.size();
    }
}
