package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchPanel extends JPanel {

    private JTextField cityNameField;
    private JButton submitButton;
    private JLabel cityNameFieldLabel;
    private SpringLayout springLayout;
    private final MainFrame FRAME;

    public SearchPanel(MainFrame frame) {
        this.FRAME = frame;
        configureTextField();
        configureButton();
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

    private void configureButton() {
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                FRAME.submit(URLEncoder.encode(cityNameField.getText(), StandardCharsets.UTF_8));
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
        setLayout(springLayout);
        revalidate();
        repaint();
    }
}
