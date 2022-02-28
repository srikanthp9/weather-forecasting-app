package com.weatherforecasting.controller;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherForecastingController {

  @GetMapping(value = "/forecast/{latitude}/{longitude}")
  public String getForecast(@PathVariable String latitude,@PathVariable String longitude) throws URISyntaxException, ParseException {

    StringBuilder url = new StringBuilder("https://api.weather.gov/points/");
    url.append(latitude);
    url.append(",");
    url.append(longitude);
    
    RestTemplate restTemplate = new RestTemplate();
    URI uri = new URI(url.toString());
    ResponseEntity<String> forecastResponse = restTemplate.getForEntity(uri, String.class);

    String response = forecastResponse.getBody();
    //System.out.println(forecastResponse.getBody());

    JSONParser parser = new JSONParser(response);

    Map<String,Object> properties = (Map<String, Object>) parser.object().get("properties");
    
    String forecastUrl = (String) properties.get("forecast");
    
    //System.out.println(forecastUrl);
    
    uri = new URI(forecastUrl);
    ResponseEntity<String> forecastResponse1 = restTemplate.getForEntity(uri, String.class);

    //System.out.println(forecastResponse1.getBody());
    
    
    String response1 = forecastResponse1.getBody();
    //System.out.println(forecastResponse.getBody());

    JSONParser parser1 = new JSONParser(response1);

    Map<String,Object> properties1 = (Map<String, Object>) parser1.object().get("properties");
    
    ArrayList<LinkedHashMap> periods = (ArrayList<LinkedHashMap>) properties1.get("periods");
        
    java.math.BigInteger temp = null;
    for(Map abc : periods) {
      if(abc.get("name").equals("Wednesday Night")) {
        temp = (BigInteger) abc.get("temperature");
      }
    }

    return temp.toString();

  }

}
