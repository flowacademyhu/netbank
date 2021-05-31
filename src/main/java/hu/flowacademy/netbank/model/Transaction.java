package hu.flowacademy.netbank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account receiver;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false, columnDefinition = "DECIMAL(19,2) DEFAULT 0")
    private BigDecimal currentExchangeRate;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.FAILED;
    private LocalDateTime createdAt;
}
