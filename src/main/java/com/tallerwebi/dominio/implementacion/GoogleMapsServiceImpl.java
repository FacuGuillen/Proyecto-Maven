package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.config.GoogleMapsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsServiceImpl {
    private final String apiKey;

    @Autowired
    public GoogleMapsServiceImpl(GoogleMapsConfig googleMapsConfig) {
        this.apiKey = googleMapsConfig.getApiKey();
    }
}
