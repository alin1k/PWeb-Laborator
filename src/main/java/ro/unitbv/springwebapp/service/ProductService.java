package ro.unitbv.springwebapp.service;

import org.springframework.stereotype.Service;
import ro.unitbv.springwebapp.dto.CreateProductRequest;
import ro.unitbv.springwebapp.model.Product;
import ro.unitbv.springwebapp.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product create(CreateProductRequest request) throws IllegalArgumentException {
        if (request.getPrice() <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }

        if(request.getStock() < 0){
            throw new IllegalArgumentException("Stock must not be negative");
        }

        Product product = new Product(request.getName(), request.getPrice(), request.getStock());
        return productRepository.save(product);
    }

    public Product update(Integer id, Product product) throws IllegalArgumentException {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (product.getPrice() <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }

        if(product.getStock() < 0){
            throw new IllegalArgumentException("Stock must not be negative");
        }

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

        return productRepository.save(existing);
    }

    public boolean deleteById(Integer id) {
        if (!productRepository.existsById(id)) return false;
        productRepository.deleteById(id);
        return true;
    }

    public int productsCount(){
        return productRepository.findAll().size();
    }
}
