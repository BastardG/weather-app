package ru.bastard.weather.gui;

import ru.bastard.weather.dto.WeatherDTO;

import javax.swing.*;
import java.awt.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class InfoPanel extends JPanel {

    JEditorPane editorPane;

    public InfoPanel(WeatherDTO weatherDTO, String cityName) {
        this.editorPane = new JEditorPane();
        editorPane.setPreferredSize(new Dimension(240, 120));
        editorPane.setEditable(false);
        editorPane.setText(String.format("Current Weather in %s\nTemperature: %s c\nWind Speed: %s km/h\nWind Direction: %s\nWeather Condition: %s", URLDecoder.decode(cityName, StandardCharsets.UTF_8), weatherDTO.getTemperature(), weatherDTO.getWindSpeed(),
                weatherDTO.getWindDirection(), weatherDTO.getWeatherCode().getName()));
        add(editorPane);
    }

}
