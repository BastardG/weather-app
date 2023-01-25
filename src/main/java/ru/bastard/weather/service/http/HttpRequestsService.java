package ru.bastard.weather.service.http;

import org.springframework.beans.factory.annotation.Autowired;
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

    public WeatherDTO getWeather(String cityName) {
        var geoDTO = getGeo(cityName);
        if(geoDTO == null){
            throw new NullPointerException("City doesn't find");
        } else {
            try {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String weatherUrl = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min&timezone=auto&start_date=%s&end_date=%s&current_weather=true",
                        geoDTO.getLat(), geoDTO.getLon(),
                        sdf.format(date), sdf.format(date));
                String json = restTemplate.getForObject(new URI(weatherUrl), String.class);
                System.out.println(geoDTO);
                var weatherDTO = WeatherMapping.JsonToWeatherDTO(json);
                return weatherDTO;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
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
