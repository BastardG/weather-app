package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bastard.weather.service.http.HttpRequestsService;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchCityFrame extends JFrame {

    private JTextField cityNameField;
    private JButton submitButton;
    private JPanel searchPanel;
    private InfoPanel infoPanel;
    private JLabel cityNameFieldLabel;
    @Autowired
    private HttpRequestsService httpRequestsService;

    public SearchCityFrame() {
        setTitle("title");
        configureFrame();
        configureLabel();
        configureButton();
        configureTextField();
        configurePanels();
    }

    private void configureLabel() {
        cityNameFieldLabel = new JLabel();
        cityNameFieldLabel.setText("Input city name");
        cityNameFieldLabel.setVisible(true);
        cityNameFieldLabel.setLabelFor(cityNameField);
    }

    private void configureFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 125);
        setVisible(true);
        setResizable(false);
    }

    private void configurePanels() {
        searchPanel = new JPanel();
        searchPanel.setName("search");
        searchPanel.setVisible(true);
        searchPanel.add(cityNameField);
        searchPanel.add(submitButton);
        searchPanel.add(cityNameFieldLabel);
        add(searchPanel);
        setLayout(new CardLayout());
        searchPanel.updateUI();
    }

    private void configureTextField() {
        cityNameField = new JTextField();
        cityNameField.setVisible(true);
        cityNameField.setName("dfgdf");
        cityNameField.setBounds(150, 125, 75, 35);
        cityNameField.setPreferredSize(new Dimension(100, 25));
    }

    private void configureButton() {
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            submit(URLEncoder.encode(cityNameField.getText(), StandardCharsets.UTF_8));
            cityNameField.setText("");
        });
        submitButton.setVisible(true);
        submitButton.setSize(100, 50);
        submitButton.setMaximumSize(new Dimension(100, 50));
        submitButton.setText("Submit");
    }

    private void submit(String cityName){
        var weatherDto = httpRequestsService.getWeather(cityName);
        infoPanel = new InfoPanel(weatherDto, cityName);
        infoPanel.setName("info");
        add(infoPanel);
        searchPanel.setVisible(false);
        infoPanel.setVisible(true);
        JButton backToSearchButton = new JButton();
        backToSearchButton.setText("Back to Search");
        backToSearchButton.setVisible(true);
        backToSearchButton.addActionListener(a -> {
            infoPanel.setVisible(false);
            searchPanel.setVisible(true);
            setSize(300, 125);
            validate();
            infoPanel = null;
        });
        infoPanel.add(backToSearchButton);
        setSize(500, 250);
        validate();
    }

}
