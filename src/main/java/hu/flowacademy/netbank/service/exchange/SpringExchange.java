package hu.flowacademy.netbank.service.exchange;

import hu.flowacademy.netbank.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
public class SpringExchange implements ExchangeStrategy {

    private final RestTemplate restTemplate;
    private final String url;

    @Override
    public BigDecimal exchange(Currency from, Currency to) {
        return Optional.ofNullable(restTemplate.getForObject(URI.create(
                String.format(url + "?from=%s&to=%s", from.name(), to.name())
        ), Exchange.class))
                .map(Exchange::getRates)
                .map(rates -> rates.get(to.name()))
                .orElseThrow();
    }
}
