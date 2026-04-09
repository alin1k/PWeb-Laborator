package ro.unitbv.springwebapp.mapper;

import org.springframework.stereotype.Component;
import ro.unitbv.springwebapp.dto.request.CreateProductRequest;
import ro.unitbv.springwebapp.dto.request.UpdateProductRequest;
import ro.unitbv.springwebapp.dto.response.ProductResponse;
import ro.unitbv.springwebapp.model.Product;

@Component
public class ProductMapper {
    public Product toEntity(CreateProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .build();
    }

    public void updateEntity(Product product, UpdateProductRequest request) {
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
    }

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .build();
    }
}