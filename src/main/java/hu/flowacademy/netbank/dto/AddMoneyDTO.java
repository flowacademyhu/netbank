package hu.flowacademy.netbank.dto;

import hu.flowacademy.netbank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMoneyDTO {
    private Currency currency;
    private BigDecimal amount;
}
