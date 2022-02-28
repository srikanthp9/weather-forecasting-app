package com.weatherforecasting.controller;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

	@GetMapping(value = "/temperature/{latitude}/{longitude}")
	public String getTemperature(@PathVariable String latitude, @PathVariable String longitude)
			throws URISyntaxException, ParseException {
		return getForecast(latitude, longitude, "temperature");
	}

	@GetMapping(value = "/windspeed/{latitude}/{longitude}")
	public String getWindspeed(@PathVariable String latitude, @PathVariable String longitude)
			throws URISyntaxException, ParseException {
		return getForecast(latitude, longitude, "windspeed");
	}

	private String getForecast(String latitude, String longitude, String parameter)
			throws URISyntaxException, ParseException {
		RestTemplate restTemplate = new RestTemplate();

		if (!isValidLatitude(latitude) || !isValidLongitude(longitude)) {
			return "Invalid input";
		}

		StringBuilder url = new StringBuilder("https://api.weather.gov/points/");
		url.append(latitude);
		url.append(",");
		url.append(longitude);
		URI uri = new URI(url.toString());
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		String responseStr = response.getBody();
		JSONParser parser = new JSONParser(responseStr);

		Map<String, Object> properties = (Map<String, Object>) parser.object().get("properties");
		String forecastUrl = (String) properties.get("forecast");
		URI fcstUri = new URI(forecastUrl);

		ResponseEntity<String> forecastResponse = restTemplate.getForEntity(fcstUri, String.class);
		String fcstResponse = forecastResponse.getBody();
		JSONParser fcstResponseParser = new JSONParser(fcstResponse);

		Map<String, Object> properties1 = (Map<String, Object>) fcstResponseParser.object().get("properties");
		List<LinkedHashMap> periods = (ArrayList<LinkedHashMap>) properties1.get("periods");
		java.math.BigInteger temp = null;
		String temperatureUnit = null;
		for (Map abc : periods) {
			if (abc.get("name").equals("Wednesday Night")) {
				temp = (BigInteger) abc.get(parameter);
				temperatureUnit = (String) abc.get("temperatureUnit");
				break;
			}
		}
		return temp.toString() + temperatureUnit;
	}

	private boolean isValidLongitude(String longitude) {
		return true;
	}

	private boolean isValidLatitude(String latitude) {
		return true;
	}

}
