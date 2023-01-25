package ru.bastard.weather.mapping;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bastard.weather.dto.GeoDTO;

public class GeoMapping {

    public static GeoDTO JsonToGeoDTO(String json){
        GeoDTO geoDTO = new GeoDTO();
        JSONArray jsonArray = new JSONArray(json);
        JSONObject firstObject = (JSONObject) jsonArray.get(0);
        geoDTO.setLat(firstObject.getDouble("lat"));
        geoDTO.setLon(firstObject.getDouble("lon"));
        return geoDTO;
    }
}
