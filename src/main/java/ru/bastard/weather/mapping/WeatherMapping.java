package ru.bastard.weather.mapping;

import org.json.JSONObject;
import ru.bastard.weather.dto.WeatherDTO;
import ru.bastard.weather.dto.enums.WeatherCode;

public class WeatherMapping {

    public static WeatherDTO JsonToWeatherDTO(String json){
        var retirement = new WeatherDTO();
        var root = new JSONObject(json);
        var current = root.getJSONObject("current_weather");
        var daily = root.getJSONObject("daily");

        retirement.setLat(root.getDouble("latitude"));
        retirement.setLon(root.getDouble("longitude"));
        retirement.setTemperature(current.getDouble("temperature"));
        retirement.setWindSpeed(current.getDouble("windspeed"));
        retirement.setWindDirection(windDirectionToString(current.getInt("winddirection")));
        retirement.setWeatherCode(WeatherCode.getWeatherCode(current.getInt("weathercode")));
        retirement.setMaxDailyTemperature(daily.getJSONArray("temperature_2m_max").getDouble(0));
        retirement.setMinDailyTemperature(daily.getJSONArray("temperature_2m_min").getDouble(0));
        retirement.setMaxDailyFeelsLike(daily.getJSONArray("apparent_temperature_max").getDouble(0));
        retirement.setMinDailyFeelsLike(daily.getJSONArray("apparent_temperature_min").getDouble(0));
        return retirement;
    }

    private static String windDirectionToString(int degrees){
        if(degrees > 340 || degrees < 20){
            return "North";
        } else if(degrees > 70 && degrees < 110) {
            return "East";
        } else if(degrees > 160 && degrees < 200) {
            return "South";
        } else if(degrees > 250 && degrees < 290) {
            return "West";
        } else if(degrees > 20 && degrees < 70){
            return "North-East";
        } else if(degrees > 110 && degrees < 160){
            return "South-East";
        } else if(degrees > 200 && degrees < 250){
            return "South-West";
        } else {
            return "North-West";
        }
    }
}
