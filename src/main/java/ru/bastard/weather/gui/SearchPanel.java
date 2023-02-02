package ru.bastard.weather.gui;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class SearchPanel extends JPanel {

    private JTextField cityNameField;
    private JButton submitButton;
    private JButton toDefaultButton;
    private JLabel cityNameFieldLabel;
    private SpringLayout springLayout;
    private final MainFrame FRAME;

    public SearchPanel(MainFrame frame) {
        this.FRAME = frame;
        configureTextField();
        configureButtons();
        configureLabel();
        configureThis();
        configureLayout();
        updateUI();
    }

    private void configureThis() {
        setName("search");
        setVisible(true);
        add(cityNameFieldLabel);
        add(cityNameField);
        add(submitButton);
        add(toDefaultButton);
        setPreferredSize(new Dimension(FRAME.getHeight(), FRAME.getWidth()));
        updateUI();
    }

    private void configureTextField() {
        cityNameField = new JTextField();
        cityNameField.setVisible(true);
        cityNameField.setName("searchField");
        cityNameField.setBounds(150, 125, 75, 35);
        cityNameField.setPreferredSize(new Dimension(100, 25));
    }

    private void configureButtons() {
        submitButton = new JButton("Submit");
        toDefaultButton = new JButton("To Default");
        submitButton.addActionListener(e -> {
            try {
                FRAME.submit(URLEncoder.encode(cityNameField.getText(), StandardCharsets.UTF_8));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            cityNameField.setText("");
        });
        toDefaultButton.addActionListener(e -> {
            try {
                var cityName = cityNameField.getText();
                putDefault(cityName);
                FRAME.submit(URLEncoder.encode(cityName, StandardCharsets.UTF_8));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            cityNameField.setText("");
        });
        submitButton.setVisible(true);
        toDefaultButton.setVisible(true);
        submitButton.setSize(100, 50);
        toDefaultButton.setSize(50, 50);
        submitButton.setMaximumSize(new Dimension(100, 50));
        toDefaultButton.setMaximumSize(new Dimension(50, 50));
        submitButton.setText("Submit");
    }

    private void putDefault(String cityName) {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("config.json");
        System.out.println(resourceAsStream);
        try {
            URI uri = getClass().getClassLoader().getResource("config.json").toURI();

            String temp = FileUtils.readFileToString(new File(uri), StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(temp);
            jsonObject.put("default", cityName);
            FileUtils.write(new File(uri), "", StandardCharsets.UTF_8);
            FileUtils.write(new File(uri), jsonObject.toString(), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void configureLabel() {
        cityNameFieldLabel = new JLabel();
        cityNameFieldLabel.setText("Input city name");
        cityNameFieldLabel.setVisible(true);
        cityNameFieldLabel.setLabelFor(cityNameField);
    }

    private void configureLayout() {
        springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cityNameFieldLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, cityNameFieldLabel, -85, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cityNameField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, cityNameField, -60, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submitButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, submitButton, 50, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.WEST, toDefaultButton, 10, SpringLayout.EAST, cityNameField);
        springLayout.putConstraint(SpringLayout.EAST, toDefaultButton, 30, SpringLayout.EAST, cityNameField);
        springLayout.putConstraint(SpringLayout.NORTH, toDefaultButton, 0, SpringLayout.NORTH, cityNameField);
        springLayout.putConstraint(SpringLayout.SOUTH, toDefaultButton, 0, SpringLayout.SOUTH, cityNameField);
        setLayout(springLayout);
        revalidate();
        repaint();
    }
}
