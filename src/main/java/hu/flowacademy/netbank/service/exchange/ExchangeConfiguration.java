package hu.flowacademy.netbank.service.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class ExchangeConfiguration {

    @Value("${app.exchangeStrategy}")
    private String strategy;

    @Value("${app.exchangeUrl}")
    private String url;

    @Bean
    public ExchangeStrategy exchangeStrategy() {
        if ("jdk".equals(strategy)) {
            return new JDKExchange(HttpClient.newHttpClient(), new ObjectMapper(), url);
        } else {
            return new SpringExchange(new RestTemplate(), url);
        }
    }

}
