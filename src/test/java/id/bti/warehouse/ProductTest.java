package id.bti.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import id.bti.warehouse.constant.ProductConstant;
import id.bti.warehouse.constant.UserConstant;
import id.bti.warehouse.dto.request.UserRequest;
import id.bti.warehouse.entity.Product;
import id.bti.warehouse.entity.User;
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

}
