package ro.unitbv.springwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.unitbv.springwebapp.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
