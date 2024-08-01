package id.bti.warehouse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import id.bti.warehouse.entity.Product;
import id.bti.warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        Optional<Product> productOpt = productRepository.findByName(product.getName());

        if (!productOpt.isEmpty()) {
            String id = productOpt.get().getId();
            Integer quantity = productOpt.get().getQuantity() + product.getQuantity();

            product.setId(id);
            product.setQuantity(quantity);
        }

        return productRepository.save(product);

    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);

    }

    public void deleteById(String id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new RuntimeException("Produk tidak ditemukan");
        }

        productRepository.deleteById(id);
    }

}
