package ro.unitbv.springwebapp.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStockRequest {
    @Min(value = 0, message = "Stocul nu poate fi negativ.")
    private int stock;
}
