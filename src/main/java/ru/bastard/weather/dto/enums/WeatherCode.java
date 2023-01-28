package ru.bastard.weather.dto.enums;

import java.util.*;
import java.util.stream.Collectors;

public enum WeatherCode {

    CLEAR_SKY("Clear Sky", 0),
    MAINLY_CLEAR("Mainly Clear", 1),
    PARTLY_CLOUDY("Partly Cloudy", 2),
    OVERCAST("Overcast", 3),
    FOG("Fog", 45, 48),
    DRIZZLE("Drizzle", 51, 53),
    HEAVY_DRIZZLE("Heavy Drizzle", 55),
    FREEZING_DRIZZLE("Freezing Drizzle", 56, 57),
    LIGHT_RAIN("Slightly Rain", 61),
    MODERATE_RAIN("Moderate Rain", 63),
    HEAVY_RAIN("Heavy Rain", 65),
    FREEZING_RAIN("Freezing Rain", 66, 67),
    LIGHT_SNOW("Slightly Snow", 71),
    MODERATE_SNOW("Moderate Snow", 73),
    HEAVY_SNOW("Heavy Snow", 75),
    SNOW_GRAINS("Snow Grains", 77),
    SLIGHT_RAIN_SHOWER("Slight Rain Showers", 80),
    MODERATE_RAIN_SHOWER("Moderate Rain Showers", 81),
    VIOLENT_RAIN_SHOWER("Violent Rain Showers", 82),
    SLIGHT_SNOW_SHOWER("Slight Snow Showers", 85),
    HEAVY_SNOW_SHOWER("Heavy Snow Showers", 86);

    private String name;
    private List<Integer> weatherCodes;
    private static final Map<Integer, WeatherCode> weatherCodeMap = new HashMap<>();

    static {
        for(WeatherCode wCode : WeatherCode.values()){
            var codes = wCode.getWeatherCodes();
            for(int i : codes){
                weatherCodeMap.put(i, wCode);
            }
        }
    }

    WeatherCode(String name, int... codesArray) {
        this.name = name;
        weatherCodes = new ArrayList<>();
        weatherCodes.addAll(Arrays.stream(codesArray).boxed().collect(Collectors.toList()));
    }

    public static WeatherCode getWeatherCode(int weatherCode){
        return weatherCodeMap.get(weatherCode);
    }

    public String getName() {
        return name;
    }

    public List<Integer> getWeatherCodes() {
        return new ArrayList<>(weatherCodes);
    }

}
