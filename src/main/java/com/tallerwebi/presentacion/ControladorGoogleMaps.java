package com.tallerwebi.presentacion;

import com.tallerwebi.config.GoogleMapsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorGoogleMaps {

    private GoogleMapsConfig googleMapsConfig;

    @Autowired
    public ControladorGoogleMaps(GoogleMapsConfig googleMapsConfig) {
        this.googleMapsConfig = googleMapsConfig;
    }

    @RequestMapping(value = "/showMap")
    public ModelAndView showMap(){
        ModelMap model = new ModelMap();
        String apikey = googleMapsConfig.getApiKey();
        model.put("apikey", apikey);
        return new ModelAndView( "showMap",model);
    }
}
