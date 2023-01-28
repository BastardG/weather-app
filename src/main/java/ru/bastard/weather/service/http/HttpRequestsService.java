package ru.bastard.weather.service.http;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bastard.weather.dto.GeoDTO;
import ru.bastard.weather.dto.WeatherDTO;
import ru.bastard.weather.mapping.GeoMapping;
import ru.bastard.weather.mapping.WeatherMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public record HttpRequestsService(RestTemplate restTemplate) {

    public WeatherDTO getWeather(String cityName) throws Exception {
        GeoDTO geoDTO = getGeo(cityName);
        if(geoDTO == null){
            throw new Exception("City doesn't find, or your city name is wrong");
        } else {
            try {
                var weatherDTO = WeatherMapping.JsonToWeatherDTO(getJsonToWeatherDto(geoDTO));
                return weatherDTO;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getJsonToWeatherDto(GeoDTO geoDTO) throws URISyntaxException {
        var date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String weatherUrl = String.format(
                "https://api.open-meteo.com/v1/forecast?" +
                        "latitude=%s" + //lat
                        "&longitude=%s" + //lon
                        "&daily=temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min" +
                        "&timezone=auto" +
                        "&start_date=%s" + //date with format: yyyy-MM-dd
                        "&end_date=%s" +  //same date with same format
                        "&current_weather=true",
                geoDTO.getLat(), geoDTO.getLon(),
                simpleDateFormat.format(date), simpleDateFormat.format(date));
        var jsonString = restTemplate.getForObject(new URI(weatherUrl), String.class);
        return jsonString;
    }

    private GeoDTO getGeo(String cityName) {
        try {
            String json = restTemplate.getForObject(
                    new URI("https://nominatim.openstreetmap.org/search?city="+cityName+"&format=json"), String.class);
            return GeoMapping.JsonToGeoDTO(json);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
