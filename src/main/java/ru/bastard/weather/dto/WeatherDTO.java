package ru.bastard.weather.dto;

import lombok.Data;
import ru.bastard.weather.dto.enums.WeatherCode;

@Data
public class WeatherDTO {

    private double lat;
    private double lon;
    private double temperature;
    private double windSpeed;
    private String windDirection;
    private WeatherCode weatherCode;
    private double maxDailyTemperature;
    private double minDailyTemperature;
    private double maxDailyFeelsLike;
    private double minDailyFeelsLike;

}
