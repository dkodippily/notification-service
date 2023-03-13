package com.rk.email.enrich;

import com.rk.email.config.AppConfiguration;
import com.rk.email.dto.QuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Dayan Kodippily
 */

@Component
public class EnrichServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichServiceClient.class);

    @Autowired
    private AppConfiguration appConfiguration;
    private RestTemplate restTemplate;

    public EnrichServiceClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public QuoteDto getQuote() {
        LOGGER.debug("Calling EnrichServiceClient");
        ResponseEntity<QuoteDto[]> randomQuote = null;
        try {
            RequestEntity<?> request = RequestEntity.get(URI.create(appConfiguration.getEnrichApi())).build();
            randomQuote = restTemplate.exchange(request, QuoteDto[].class);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error calling EnrichServiceClient", e.getCause());
        }

        return randomQuote != null && randomQuote.getBody() != null ? randomQuote.getBody()[0] : null;
    }

}
