package com.raken.email.service;

import com.raken.email.enrich.EnrichServiceClient;
import org.springframework.stereotype.Service;

/**
 * @author Dayan Kodippily
 */

@Service
public class EnrichServiceImpl implements EnrichService {


    private EnrichServiceClient enrichServiceClient;

    public EnrichServiceImpl(EnrichServiceClient enrichServiceClient) {
        this.enrichServiceClient = enrichServiceClient;
    }

    @Override
    public String getQuoteOfTheDay() {
        return enrichServiceClient.getQuote().getH();
    }
}
