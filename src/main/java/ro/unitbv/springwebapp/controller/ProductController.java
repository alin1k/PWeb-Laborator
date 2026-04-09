package ro.unitbv.springwebapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unitbv.springwebapp.dto.request.CreateProductRequest;
import ro.unitbv.springwebapp.dto.request.UpdateProductRequest;
import ro.unitbv.springwebapp.dto.request.UpdateStockRequest;
import ro.unitbv.springwebapp.dto.response.ProductResponse;
import ro.unitbv.springwebapp.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        ProductResponse createdProduct = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @Valid @RequestBody UpdateProductRequest request) {
        return productService.update(id, request);
    }

    @PutMapping("/stock/{id}")
    public ProductResponse updateProductStock(@PathVariable Integer id, @Valid @RequestBody UpdateStockRequest request){
        return productService.updateStock(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public int countProducts(){
        return productService.productsCount();
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProductByName(@RequestParam(required = false) String name){
        // cautarea dupa nume deja a fost implementata
        if(name == null) {
            return productService.findAll();
        }

        return productService.findByName(name);
    }

    @GetMapping("/cheaper-than")
    public List<ProductResponse> getCheaperThan(@RequestParam(required = false) Double price) {
        if(price == null){
            return productService.findAll();
        }

        return productService.findCheaperThan(price);
    }
}
