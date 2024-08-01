package id.bti.warehouse.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRequest {

    private String id;
    @Column(name = "user_id")
    private String UserId;
    @Column(name = "product_id")
    private String productId;
    private Integer quantity;
    private String status;

}
