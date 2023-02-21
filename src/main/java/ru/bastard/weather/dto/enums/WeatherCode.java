package ru.bastard.weather.dto.enums;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public enum WeatherCode {

    CLEAR_SKY("Clear Sky", "Чистое небо", 0),
    MAINLY_CLEAR("Mainly Clear", "В основном чистое небо", 1),
    PARTLY_CLOUDY("Partly Cloudy", "Местами облачно", 2),
    OVERCAST("Overcast", "Пасмурно", 3),
    FOG("Fog", "Туман",45, 48),
    DRIZZLE("Drizzle", "Морось", 51, 53),
    HEAVY_DRIZZLE("Heavy Drizzle", "Сильная морось", 55),
    FREEZING_DRIZZLE("Freezing Drizzle", "Изморозь", 56, 57),
    LIGHT_RAIN("Slightly Rain", "Легкий дождь", 61),
    MODERATE_RAIN("Moderate Rain", "Умеренный дождь", 63),
    HEAVY_RAIN("Heavy Rain", "Сильный дождь", 65),
    FREEZING_RAIN("Freezing Rain", "Ледяной дождь", 66, 67),
    LIGHT_SNOW("Slightly Snow", "Лёгкий снег", 71),
    MODERATE_SNOW("Moderate Snow", "Умеренный снег", 73),
    HEAVY_SNOW("Heavy Snow", "Снегопад", 75),
    SNOW_GRAINS("Snow Grains", "Снежные зерна", 77),
    SLIGHT_RAIN_SHOWER("Slight Rain Showers", "Слабый ливень",80),
    MODERATE_RAIN_SHOWER("Moderate Rain Showers", "Умеренный ливень", 81),
    VIOLENT_RAIN_SHOWER("Violent Rain Showers", "Сильный ливень", 82),
    SLIGHT_SNOW_SHOWER("Slight Snow Showers", "Небольшой снегопад", 85),
    HEAVY_SNOW_SHOWER("Heavy Snow Showers", "Сильный снегопад", 86);

    private String name;
    private String ruName;
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

    WeatherCode(String name, String ruName, int... codesArray) {
        this.name = name;
        this.ruName = ruName;
        weatherCodes = new ArrayList<>();
        weatherCodes.addAll(Arrays.stream(codesArray).boxed().collect(Collectors.toList()));
    }

    public static WeatherCode getWeatherCode(int weatherCode){
        return weatherCodeMap.get(weatherCode);
    }

    public String getName() {
        return name;
    }

    public byte[] getRuName() throws UnsupportedEncodingException {return ruName.getBytes("CP1251");}

    public List<Integer> getWeatherCodes() {
        return new ArrayList<>(weatherCodes);
    }

}
