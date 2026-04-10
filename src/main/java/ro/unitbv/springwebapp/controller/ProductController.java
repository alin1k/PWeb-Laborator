package ro.unitbv.springwebapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        log.info("A fost apelat endpoint-ul GET /api/products");
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id) {
        log.info("A fost apelat endpoint-ul GET /api/products/{}", id);
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        log.info("A fost apelat endpoint-ul POST /api/products pentru produsul cu numele={}", request.getName());
        ProductResponse createdProduct = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @Valid @RequestBody UpdateProductRequest request) {
        log.info("A fost apelat endpoint-ul PUT /api/products/{}", id);
        return productService.update(id, request);
    }

    @PutMapping("/stock/{id}")
    public ProductResponse updateProductStock(@PathVariable Integer id, @Valid @RequestBody UpdateStockRequest request){
        log.info("A fost apelat endpoint-ul PUT /api/products/stock/{}", id);
        return productService.updateStock(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        log.info("A fost apelat endpoint-ul DELETE /api/products/{}", id);
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public int countProducts(){
        log.info("A fost apelat endpoint-ul GET /api/products/count");
        return productService.productsCount();
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProductByName(@RequestParam(required = false) String name){
        log.info("A fost apelat endpoint-ul GET /api/products/search pentru query-ul {}", name);
        if(name == null) {
            return productService.findAll();
        }

        return productService.findByName(name);
    }

    @GetMapping("/cheaper-than")
    public List<ProductResponse> getCheaperThan(@RequestParam(required = false) Double price) {
        log.info("A fost apelat endpoint-ul GET /api/products/cheaper-than pentru pretul {}", price);
        if(price == null){
            return productService.findAll();
        }

        return productService.findCheaperThan(price);
    }
}
