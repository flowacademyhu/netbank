package hu.flowacademy.netbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateDTO {
    private String senderAccountId;
    private String receiverAccountId;
    private BigDecimal amount;
}
