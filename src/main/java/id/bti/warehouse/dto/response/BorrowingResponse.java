package id.bti.warehouse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingResponse {

    private String fullName;
    private String productName;
    private Integer quantity;
    private String status;

}
