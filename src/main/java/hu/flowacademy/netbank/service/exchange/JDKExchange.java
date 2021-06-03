package hu.flowacademy.netbank.service.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.flowacademy.netbank.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

@RequiredArgsConstructor
public class JDKExchange implements ExchangeStrategy {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String url;

    @SneakyThrows
    @Override
    public BigDecimal exchange(Currency from, Currency to) {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(
                        String.format(url + "?from=%s&to=%s", from.name(), to.name())
                )
        )
                .GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(Charset.forName("UTF-8")));
        Exchange exchange = objectMapper.readValue(response.body(), Exchange.class);
        return exchange.getRates().get(to.name());
    }

}
