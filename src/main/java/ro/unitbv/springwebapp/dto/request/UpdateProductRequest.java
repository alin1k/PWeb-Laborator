package ro.unitbv.springwebapp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    @NotBlank(message = "Numele produsului este obligatoriu.")
    @Size(max = 100, message = "Numele produsului nu poate depasi 100 de caractere.")
    private String name;

    @DecimalMin(value = "0.01", message = "Pretul trebuie sa fie mai mare decat 0.")
    private double price;

    @Min(value = 0, message = "Stocul nu poate fi negativ.")
    private int stock;
}
