package id.bti.warehouse.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("user_id")
    private String UserId;
    @JsonProperty("product_id")
    private String productId;
    private Integer quantity;
    private String status;

}
