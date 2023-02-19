package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bastard.weather.Main;
import ru.bastard.weather.dto.WeatherDTO;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class InfoPanel extends JPanel {

    JTextPane textPane;
    SpringLayout springLayout;
    JButton backToSearchButton;

    public InfoPanel(WeatherDTO weatherDTO, String cityName) {
        init();
        configurePane(weatherDTO, cityName);
        configureButton();
        configureLayout();
    }

    private void init() {
        textPane = new JTextPane();
        springLayout = new SpringLayout();
        backToSearchButton = new JButton();
        add(textPane);
        add(backToSearchButton);
        setLayout(springLayout);
    }

    private void configureButton(){
        backToSearchButton.setText(MainFrame.language.getString("backToSearch"));
        backToSearchButton.setVisible(true);
        backToSearchButton.addActionListener(a -> {
            Main.MAIN_FRAME.exchangeInfo();
        });
    }

    private void configureLayout(){
        springLayout.putConstraint(SpringLayout.WEST, textPane, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, textPane, 0, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backToSearchButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, backToSearchButton, 80, SpringLayout.VERTICAL_CENTER, this);
    }

    private void configurePane(WeatherDTO weatherDTO, String cityName){
        textPane.setPreferredSize(new Dimension(240, 120));
        textPane.setEditable(false);
        /*
        "Current Weather in %s" +
                        "\nTemperature: %s c\nWind Speed: %s km/h" +
                        "\nWind Direction: %s" +
                        "\nWeather Condition: %s"
         */
        textPane.setText(String.format(MainFrame.language.getString("textPane"),
                URLDecoder.decode(cityName, StandardCharsets.UTF_8),
                weatherDTO.getTemperature(), weatherDTO.getWindSpeed(),
                weatherDTO.getWindDirection(), weatherDTO.getWeatherCode().getName()));
        var doc = textPane.getStyledDocument();
        var sat = new SimpleAttributeSet();
        StyleConstants.setAlignment(sat, StyleConstants.ALIGN_CENTER);
        StyleConstants.setBold(sat, true);
        textPane.setEditable(false);
        doc.setParagraphAttributes(0, doc.getLength(), sat, false);
    }

}
