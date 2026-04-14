package ro.unitbv.springwebapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.unitbv.springwebapp.dto.request.CreateProductRequest;
import ro.unitbv.springwebapp.dto.request.UpdateProductRequest;
import ro.unitbv.springwebapp.dto.request.UpdateStockRequest;
import ro.unitbv.springwebapp.dto.response.ProductResponse;
import ro.unitbv.springwebapp.exception.ProductNotFoundException;
import ro.unitbv.springwebapp.mapper.ProductMapper;
import ro.unitbv.springwebapp.model.Product;
import ro.unitbv.springwebapp.repository.ProductRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final MeterRegistry meterRegistry;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @PostConstruct
    public void initMetrics() {
        meterRegistry.gauge("products.total.count",
                productRepository,
                repo -> repo.count());
    }

    public List<ProductResponse> findAll() {
        log.debug("Se solicita cautarea tuturor produselor");
        List<ProductResponse> result =  productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();

        log.info("Se returneaza {} produse", result.size());
        return result;
    }

    public ProductResponse findById(Integer id) {
        log.debug("Se cauta produsul cu id={}" , id);

        return Timer.builder("products.findById.time")
                .register(meterRegistry)
                .record(()->{
                    Product product = productRepository.findById(id)
                            .orElseThrow(() -> {
                                log.warn("Produsul cu id={} nu a putu fi gasit", id);
                                return new ProductNotFoundException(id);
                            });

                    log.info("Produsul {} cu id={} a fost gasit", product.getName(), id);

                    return productMapper.toResponse(product);
                });
    }

    public ProductResponse create(CreateProductRequest request) {
        log.debug("Se creaza un produs nou: name={}, price={}, stock={}", request.getName(), request.getPrice(), request.getStock());
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        meterRegistry.counter("products.created.count").increment();

        log.info("Produsul {} cu id={} a fost creat cu succes", savedProduct.getName(), savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }

    public ProductResponse update(Integer id, UpdateProductRequest request) {
        log.debug("Se actualizeaza produsul cu id={}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produsul cu id={} nu a putu fi gasit", id);
                    return new ProductNotFoundException(id);
                });
        productMapper.updateEntity(existingProduct, request);
        Product updatedProduct = productRepository.save(existingProduct);

        log.info("A fost actualizat produsul cu id={}", id);
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteById(Integer id) {
        log.debug("Se incearca stergerea produsului cu id={}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Produsul cu id={} nu a putu fi gasit", id);
            throw new ProductNotFoundException(id);
        }
        log.info("Se sterge produsul cu id={}", id);
        productRepository.deleteById(id);
    }

    public int productsCount() {
        log.debug("Se cere numarul total de produse");
        int result = productRepository.findAll().size();

        log.info("Se returneaza numarul de {} produse", result);

        return result;
    }

    public List<ProductResponse> findByName(String name) {
        log.debug("Se cauta produse dupa numele {}", name);
        List<ProductResponse> result =  productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toResponse)
                .toList();

        log.info("Se returneaza {} produse pentru cautarea name={}", result.size(), name);
        return result;
    }

    public List<ProductResponse> findCheaperThan(double price){
        log.debug("Se cauta produse mai ieftine decat {}", price);
        List<ProductResponse> result = productRepository.findByPriceLessThan(price).stream()
                .map(productMapper::toResponse)
                .toList();

        log.info("Se returneaza {} produse mai ieftine decat price={}", result.size(), price);
        return result;
    }

    public ProductResponse updateStock(Integer id, UpdateStockRequest request){
        log.debug("Se actualizeaza stockul pentru produsul cu id={}, in stock={}", id, request.getStock());
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produsul cu id={} nu a putu fi gasit", id);
                    return new ProductNotFoundException(id);
                });
        productMapper.updateProductStock(existingProduct, request);
        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Stocul a fost actualizat pentru produsul cu id={}", id);
        return productMapper.toResponse(updatedProduct);
    }
}
