package ro.unitbv.springwebapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unitbv.springwebapp.dto.CreateProductRequest;
import ro.unitbv.springwebapp.dto.ProductResponse;
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
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productService.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return (product == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(new ProductResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        try{
            Product createdProduct = productService.create(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(createdProduct));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        try{
            Product updatedProduct = productService.update(id, product);
            return (updatedProduct == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(new ProductResponse(updatedProduct));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
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

    @GetMapping("/search")
    public List<ProductResponse> searchProductByName(@RequestParam(required = false) String name){
        List<Product> products;

        if(name == null){
            products = productService.findAll();
        }
        else{
            products = productService.findByName(name);
        }

        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }

    @GetMapping("/cheaper-than")
    public List<ProductResponse> getCheaperThan(@RequestParam(required = false) Double price) {
        List<Product> products;

        if(price == null){
            products =  productService.findAll();
        }else{
            products = productService.findCheaperThan(price);
        }

        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }
}
