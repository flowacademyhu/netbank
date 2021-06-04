package hu.flowacademy.netbank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private User owner;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal amount;
}
