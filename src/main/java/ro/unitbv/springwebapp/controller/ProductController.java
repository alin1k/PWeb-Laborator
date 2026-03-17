package ro.unitbv.springwebapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unitbv.springwebapp.dto.CreateProductRequest;
import ro.unitbv.springwebapp.model.Product;
import ro.unitbv.springwebapp.service.ProductService;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return (product == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        Product createdProduct = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product updatedProduct = productService.update(id, product);
        return (updatedProduct == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        boolean deleted = productService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public int countProducts(){
        return productService.productsCount();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Product> searchProductByName(@PathVariable String name){
        List<Product> products = productService.findAll();

        for (Product product : products){
            if(Objects.equals(product.getName(), name)){
                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.notFound().build();
    }
}
