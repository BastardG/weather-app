package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bastard.weather.service.http.HttpRequestsService;

import javax.swing.*;

@Component
public class MainFrame extends JFrame {

    private SearchPanel searchPanel;
    private InfoPanel infoPanel;
    @Autowired
    private HttpRequestsService httpRequestsService;

    public MainFrame() {
        setTitle("Weather");
        this.searchPanel = new SearchPanel(this);
        configureFrame();
        this.repaint();
    }

    private void configureFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(250, 250);
        setVisible(true);
        setResizable(false);
        add(searchPanel);
    }

    public void submit(String cityName) throws Exception {
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
