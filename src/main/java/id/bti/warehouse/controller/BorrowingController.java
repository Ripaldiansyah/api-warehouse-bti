package id.bti.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.bti.warehouse.dto.request.BorrowingRequest;
import id.bti.warehouse.dto.response.BorrowingResponse;
import id.bti.warehouse.service.BorrowingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @PostMapping
    public ResponseEntity<?> borrowingProduct(@RequestBody BorrowingRequest request) {
        BorrowingResponse response;
        try {
            response = borrowingService.borrowProduct(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.ok().body(response);
    }

}
