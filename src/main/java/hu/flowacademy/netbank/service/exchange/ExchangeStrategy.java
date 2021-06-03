package hu.flowacademy.netbank.service.exchange;

import hu.flowacademy.netbank.model.Currency;

import java.math.BigDecimal;

public interface ExchangeStrategy {

    BigDecimal exchange(Currency from, Currency to);

}
