package id.bti.warehouse.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import id.bti.warehouse.constant.BorrowingConstant;
import id.bti.warehouse.dto.request.BorrowingRequest;
import id.bti.warehouse.dto.response.BorrowingResponse;
import id.bti.warehouse.entity.Borrowing;
import id.bti.warehouse.entity.Product;
import id.bti.warehouse.entity.User;
import id.bti.warehouse.repository.BorrowingRepository;
import id.bti.warehouse.repository.ProductRepository;
import id.bti.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public BorrowingResponse borrowProduct(BorrowingRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        Product product = new Product();
        product.setId(productOpt.get().getId());
        product.setName(productOpt.get().getName());
        product.setQuantity(productOpt.get().getQuantity());
        product.setProductType(productOpt.get().getProductType());

        validateProductStock(request, product);

        updateProductStock(request, product);

        Optional<User> userOpt = userRepository.findById(request.getUserId());

        User user = new User();
        user.setId(userOpt.get().getId());
        user.setFullName(userOpt.get().getFullName());
        Borrowing borrowing = request.getStatus().equals(BorrowingConstant.IN) ? createBorrowing(request, user, product)
                : updateBorrowing(request);

        saveBorrowing(borrowing);

        return createBorrowingResponse(user, product, request);
    }

    private void validateProductStock(BorrowingRequest request, Product product) {
        if (request.getStatus().equals(BorrowingConstant.OUT) && product.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Stock tidak cukup");
        }
    }

    private void updateProductStock(BorrowingRequest request, Product product) {
        int newQuantity = request.getStatus().equals(BorrowingConstant.OUT)
                ? product.getQuantity() - request.getQuantity()
                : product.getQuantity() + request.getQuantity();

        product.setQuantity(newQuantity);
        productRepository.saveAndFlush(product);
    }

    private Borrowing createBorrowing(BorrowingRequest request, User user, Product product) {
        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setProduct(product);
        borrowing.setQuantity(request.getQuantity());
        borrowing.setStatus(request.getStatus());
        borrowing.setBorrowedAt(LocalDateTime.now());
        return borrowing;
    }

    private Borrowing updateBorrowing(BorrowingRequest request) {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(request.getId());
        borrowing.setQuantity(request.getQuantity());
        borrowing.setStatus(request.getStatus());
        borrowing.setReturnedAt(LocalDateTime.now());
        return borrowing;
    }

    private void saveBorrowing(Borrowing borrowing) {
        borrowingRepository.save(borrowing);
    }

    private BorrowingResponse createBorrowingResponse(User user, Product product, BorrowingRequest request) {
        return new BorrowingResponse(
                user.getFullName(),
                product.getName(),
                request.getQuantity(),
                request.getStatus());
    }

}
