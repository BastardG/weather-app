package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bastard.weather.Main;
import ru.bastard.weather.service.io.IOService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class SearchPanel extends JPanel {

    private JTextField cityNameField;
    private JButton submitButton;
    private JButton toDefaultButton;
    private JLabel cityNameFieldLabel;
    private SpringLayout springLayout;
    public JList<String> dropdownSuggestions;
    private final MainFrame FRAME;

    private static final IOService ioService = new IOService();

    public SearchPanel(MainFrame frame) {
        this.FRAME = frame;
        init();
        configureDropdownSuggestions();
        configureTextField();
        configureButtons();
        configureLabel();
        configureThis();
        configureLayout();
        updateUI();
        revalidate();
        repaint();
    }

    private void init() {
        cityNameField = new JTextField();
        submitButton = new JButton();
        toDefaultButton = new JButton("S");
        cityNameFieldLabel = new JLabel();
        springLayout = new SpringLayout();
        add(cityNameField);
        add(submitButton);
        add(toDefaultButton);
        add(cityNameFieldLabel);
        setLayout(springLayout);
    }

    private void configureDropdownSuggestions() {
        dropdownSuggestions = new JList<>();
        add(dropdownSuggestions);
        try {
            dropdownSuggestions.setListData(ioService.getLastSearches());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dropdownSuggestions.setDragEnabled(false);
        dropdownSuggestions.setVisible(false);
        updateUI();
    }

    private void configureThis() {
        setName("search");
        setVisible(true);
        setPreferredSize(new Dimension(FRAME.getHeight(), FRAME.getWidth()));
        updateUI();
    }

    private void configureTextField() {
        cityNameField.setVisible(true);
        cityNameField.setName("searchField");
        cityNameField.setBounds(150, 125, 75, 35);
        cityNameField.setPreferredSize(new Dimension(100, 25));
        configureFieldFocusListener();
        configureFieldKeyListener();
        configureFieldMouseListener();
    }

    private void configureFieldMouseListener() {
        cityNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(dropdownSuggestions.isVisible()) {
                    dropdownSuggestions.setVisible(false);
                } else {
                    dropdownSuggestions.setVisible(true);
                }
                updateUI();
            }
        });
    }

    private void configureFieldFocusListener() {
        cityNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dropdownSuggestions.setVisible(true);
                updateUI();
            }
            @Override
            public void focusLost(FocusEvent e) {
                dropdownSuggestions.setVisible(false);
                updateUI();
            }
        });
    }

    private void configureFieldKeyListener() {
        cityNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    if(dropdownSuggestions.isSelectionEmpty()) {
                        try {
                            Main.MAIN_FRAME.submit(encode(cityNameField.getText()));
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        try {
                            cityNameField.setText(dropdownSuggestions.getSelectedValue());
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } else if(e.getKeyCode() == 38) {
                    if(dropdownSuggestions.isSelectionEmpty()) {
                        dropdownSuggestions.setSelectedIndex(dropdownSuggestions.getLastVisibleIndex());
                    } else {
                        if(dropdownSuggestions.getSelectedIndex() == 0) {
                            dropdownSuggestions.setSelectedIndex(dropdownSuggestions.getLastVisibleIndex());
                        } else {
                            dropdownSuggestions.setSelectedIndex(dropdownSuggestions.getSelectedIndex() - 1);
                        }
                    }
                } else if(e.getKeyCode() == 40) {
                    if (dropdownSuggestions.isSelectionEmpty()) {
                        dropdownSuggestions.setSelectedIndex(dropdownSuggestions.getFirstVisibleIndex());
                    } else {
                        if(dropdownSuggestions.getLastVisibleIndex() == dropdownSuggestions.getSelectedIndex()) {
                            dropdownSuggestions.setSelectedIndex(dropdownSuggestions.getFirstVisibleIndex());
                        } else {
                            dropdownSuggestions.setSelectedIndex(dropdownSuggestions.getSelectedIndex() + 1);
                        }
                    }
                } else if(e.getKeyCode() == 27) {
                    dropdownSuggestions.clearSelection();
                    dropdownSuggestions.setVisible(false);
                }
            }
        });
    }

    private String encode(String incomingString) {
        return URLEncoder.encode(incomingString, StandardCharsets.UTF_8);
    }

    private void configureButtons() {
        submitButton.addActionListener(e -> {
            try {
                if(dropdownSuggestions.isSelectionEmpty()) {
                    FRAME.submit(encode(cityNameField.getText()));
                } else {
                    FRAME.submit(encode(dropdownSuggestions.getSelectedValue()));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            cityNameField.setText("");
        });
        toDefaultButton.addActionListener(e -> {
            try {
                var cityName = cityNameField.getText();
                ioService.putDefault(cityName);
                FRAME.submit(encode(cityName));
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
        submitButton.setText(MainFrame.language.getString("submitButton"));
    }

    private void configureLabel() {
        cityNameFieldLabel.setText(MainFrame.language.getString("inputLabel"));
        cityNameFieldLabel.setVisible(true);
        cityNameFieldLabel.setLabelFor(cityNameField);
    }

    private void configureLayout() {
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
        springLayout.putConstraint(SpringLayout.NORTH, dropdownSuggestions, 0, SpringLayout.SOUTH, cityNameField);
        springLayout.putConstraint(SpringLayout.WEST, dropdownSuggestions, 0, SpringLayout.WEST, cityNameField);
        springLayout.putConstraint(SpringLayout.EAST, dropdownSuggestions, 0, SpringLayout.EAST, cityNameField);
        revalidate();
        repaint();
    }
}
