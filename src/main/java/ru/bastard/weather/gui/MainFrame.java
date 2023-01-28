package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bastard.weather.service.http.HttpRequestsService;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class MainFrame extends JFrame {

    private JTextField cityNameField;
    private JButton submitButton;
    private JPanel searchPanel;
    private InfoPanel infoPanel;
    private JLabel cityNameFieldLabel;
    private SpringLayout springLayout;
    @Autowired
    private HttpRequestsService httpRequestsService;

    public MainFrame() {
        setTitle("Weather");
        configureFrame();
        configureLabel();
        configureButton();
        configureTextField();
        configurePanels();
        configureLayout();
    }

    private void configureLabel() {
        cityNameFieldLabel = new JLabel();
        cityNameFieldLabel.setText("Input city name");
        cityNameFieldLabel.setVisible(true);
        cityNameFieldLabel.setLabelFor(cityNameField);
    }

    private void configureFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(250, 250);
        setVisible(true);
        setResizable(false);
    }

    private void configurePanels() {
        searchPanel = new JPanel();
        searchPanel.setName("search");
        searchPanel.setVisible(true);
        searchPanel.add(cityNameFieldLabel);
        searchPanel.add(cityNameField);
        searchPanel.add(submitButton);
        add(searchPanel);
        searchPanel.updateUI();
        searchPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    }

    private void configureLayout() {
        springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cityNameFieldLabel, -10, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, cityNameFieldLabel, -85, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cityNameField, -10, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, cityNameField, -60, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submitButton, -10, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, submitButton, 50, SpringLayout.VERTICAL_CENTER, this);
        searchPanel.setLayout(springLayout);
        searchPanel.revalidate();
        searchPanel.repaint();
    }


    private void configureTextField() {
        cityNameField = new JTextField();
        cityNameField.setVisible(true);
        cityNameField.setName("searchField");
        cityNameField.setBounds(150, 125, 75, 35);
        cityNameField.setPreferredSize(new Dimension(100, 25));
    }

    private void configureButton() {
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                submit(URLEncoder.encode(cityNameField.getText(), StandardCharsets.UTF_8));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            cityNameField.setText("");
        });
        submitButton.setVisible(true);
        submitButton.setSize(100, 50);
        submitButton.setMaximumSize(new Dimension(100, 50));
        submitButton.setText("Submit");
    }

    private void submit(String cityName) throws Exception {
        var weatherDto = httpRequestsService.getWeather(cityName);
        infoPanel = new InfoPanel(weatherDto, cityName);
        exchangeSearch();
        setSize(500, 250);
        validate();
    }

    public void exchangeInfo() {
        remove(infoPanel);
        add(searchPanel);
        setSize(250, 250);
        revalidate();
        repaint();
    }

    public void exchangeSearch() {
        remove(searchPanel);
        add(infoPanel);
        setSize(500, 250);
        revalidate();
        repaint();
    }

}
