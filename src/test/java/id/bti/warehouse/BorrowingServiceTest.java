package id.bti.warehouse;

import id.bti.warehouse.constant.BorrowingConstant;
import id.bti.warehouse.constant.ProductConstant;
import id.bti.warehouse.dto.request.BorrowingRequest;
import id.bti.warehouse.dto.response.BorrowingResponse;
import id.bti.warehouse.entity.Borrowing;
import id.bti.warehouse.entity.Product;
import id.bti.warehouse.entity.User;
import id.bti.warehouse.repository.BorrowingRepository;
import id.bti.warehouse.repository.ProductRepository;
import id.bti.warehouse.repository.UserRepository;
import id.bti.warehouse.service.BorrowingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowProduct() {
        String productId = "f8c0795f-9e61-4655-a08b-2816298f00c0";
        String userId = "b917d461-a356-4d6b-8a93-10cee8779b45";
        int borrowQuantity = 10;

        Product product = new Product();
        product.setId(productId);
        product.setName("Pakaian anak");
        product.setQuantity(10);
        product.setProductType(ProductConstant.TIPE_PAKAIAN);

        User user = new User();
        user.setId(userId);
        user.setFullName("Ripaldiansyah");

        BorrowingRequest request = new BorrowingRequest();
        request.setProductId(productId);
        request.setUserId(userId);
        request.setQuantity(borrowQuantity);
        request.setStatus(BorrowingConstant.OUT);

        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setProduct(product);
        borrowing.setQuantity(borrowQuantity);
        borrowing.setStatus(BorrowingConstant.OUT);
        borrowing.setModifiedDate(LocalDateTime.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(borrowingRepository.save(any(Borrowing.class))).thenReturn(borrowing);

        BorrowingResponse response = borrowingService.borrowProduct(request);

        verify(productRepository).findById(productId);
        verify(userRepository).findById(userId);
        verify(borrowingRepository).save(any(Borrowing.class));

        assertNotNull(response);
        assertEquals("Ripaldiansyah", response.getFullName());
        assertEquals("Pakaian anak", response.getProductName());
        assertEquals(borrowQuantity, response.getQuantity());
        assertEquals(BorrowingConstant.OUT, response.getStatus());
    }

    @Test
    public void testBorrowProduct_InsufficientStock() {
        String productId = "f8c0795f-9e61-4655-a08b-2816298f00c0";
        String userId = "b917d461-a356-4d6b-8a93-10cee8779b45";
        int borrowQuantity = 200;

        Product product = new Product();
        product.setId(productId);
        product.setName("Pakaian anak");
        product.setQuantity(100);
        product.setProductType(ProductConstant.TIPE_PAKAIAN);

        BorrowingRequest request = new BorrowingRequest();
        request.setProductId(productId);
        request.setUserId(userId);
        request.setQuantity(borrowQuantity);
        request.setStatus(BorrowingConstant.OUT);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.borrowProduct(request);
        });

        assertEquals("Quantity tidak cukup", exception.getMessage());
    }
}
