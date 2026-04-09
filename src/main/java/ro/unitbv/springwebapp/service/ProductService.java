package ro.unitbv.springwebapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.unitbv.springwebapp.dto.request.CreateProductRequest;
import ro.unitbv.springwebapp.dto.request.UpdateProductRequest;
import ro.unitbv.springwebapp.dto.response.ProductResponse;
import ro.unitbv.springwebapp.exception.ProductNotFoundException;
import ro.unitbv.springwebapp.mapper.ProductMapper;
import ro.unitbv.springwebapp.model.Product;
import ro.unitbv.springwebapp.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponse(product);
    }

    public ProductResponse create(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    public ProductResponse update(Integer id, UpdateProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productMapper.updateEntity(existingProduct, request);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    public int productsCount() {
        return productRepository.findAll().size();
    }

    public List<ProductResponse> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public List<ProductResponse> findCheaperThan(double price){
        return productRepository.findByPriceLessThan(price).stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
