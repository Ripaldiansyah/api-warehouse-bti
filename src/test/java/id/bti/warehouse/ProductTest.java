package id.bti.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import id.bti.warehouse.constant.ProductConstant;
import id.bti.warehouse.entity.Product;
import id.bti.warehouse.repository.ProductRepository;
import id.bti.warehouse.service.ProductService;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product(
                null,
                "Product1",
                100,
                ProductConstant.TIPE_PAKAIAN);

        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Product1", savedProduct.getName());
        assertEquals(100, savedProduct.getQuantity());
        assertEquals("PAKAIAN", savedProduct.getProductType());
    }

    @Test
    public void testSaveProductWithDuplicateName() {
        Product product1 = new Product(
                null,
                "Product1",
                100,
                ProductConstant.TIPE_PAKAIAN);

        when(productRepository.findByName("Product1"))
                .thenReturn(Optional.empty());
        when(productRepository.save(product1))
                .thenReturn(product1);

        product1 = productService.saveProduct(product1);

        Product product2 = new Product(
                null,
                "Product1",
                150,
                ProductConstant.TIPE_PAKAIAN);

        when(productRepository.findByName("Product1"))
                .thenReturn(Optional.of(product1));
        Product updatedProduct = new Product(
                product1.getId(),
                "Product1",
                250,
                ProductConstant.TIPE_PAKAIAN);
        when(productRepository.save(product2))
                .thenReturn(updatedProduct);

        product2 = productService.saveProduct(product2);

        assertNotNull(product2);
        assertEquals(product1.getId(), product2.getId());
        assertEquals("Product1", product2.getName());
        assertEquals(250, product2.getQuantity());
        assertEquals(ProductConstant.TIPE_PAKAIAN, product2.getProductType());
    }

}
