package ru.bastard.weather.gui;

import ru.bastard.weather.Main;
import ru.bastard.weather.dto.WeatherDTO;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class InfoPanel extends JPanel {

    public MainFrame searchCityFrame = Main.MAIN_FRAME;

    JTextPane textPane;
    SpringLayout springLayout;
    JButton backToSearchButton;

    public InfoPanel(WeatherDTO weatherDTO, String cityName) {
        configurePane(weatherDTO, cityName);
        configureButton();
        configureLayout();
    }

    private void configureButton(){
        backToSearchButton = new JButton();
        backToSearchButton.setText("Back to search");
        backToSearchButton.setVisible(true);
        backToSearchButton.addActionListener(a -> {
            searchCityFrame.exchangeInfo();
        });
        add(backToSearchButton);
    }

    private void configureLayout(){
        springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.WEST, textPane, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, textPane, 0, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backToSearchButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, backToSearchButton, 80, SpringLayout.VERTICAL_CENTER, this);
        setLayout(springLayout);
    }

    private void configurePane(WeatherDTO weatherDTO, String cityName){
        this.textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(240, 120));
        textPane.setEditable(false);
        textPane.setText(String.format("Current Weather in %s" +
                        "\nTemperature: %s c\nWind Speed: %s km/h" +
                        "\nWind Direction: %s" +
                        "\nWeather Condition: %s",
                URLDecoder.decode(cityName, StandardCharsets.UTF_8),
                weatherDTO.getTemperature(), weatherDTO.getWindSpeed(),
                weatherDTO.getWindDirection(), weatherDTO.getWeatherCode().getName()));
        var doc = textPane.getStyledDocument();
        var sat = new SimpleAttributeSet();
        StyleConstants.setAlignment(sat, StyleConstants.ALIGN_CENTER);
        StyleConstants.setBold(sat, true);
        textPane.setEditable(false);
        doc.setParagraphAttributes(0, doc.getLength(), sat, false);
        add(textPane);
    }

}
