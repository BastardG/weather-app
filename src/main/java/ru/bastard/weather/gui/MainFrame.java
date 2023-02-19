package ru.bastard.weather.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.bastard.weather.Main;
import ru.bastard.weather.service.http.HttpRequestsService;
import ru.bastard.weather.service.io.IOService;

import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class MainFrame extends JFrame {

    private SearchPanel searchPanel;
    private InfoPanel infoPanel;
    private HttpRequestsService httpRequestsService;
    private static final IOService ioService = new IOService();
    public static ResourceBundle language;

    @Autowired
    public MainFrame(ResourceBundle resourceBundle) {
        language = resourceBundle;
        setTitle(MainFrame.language.getString("title"));
        init();
        configureFrame();
        repaint();
    }

    private void init() {
        searchPanel = new SearchPanel(this);
        httpRequestsService = new HttpRequestsService(new RestTemplate());
        add(searchPanel);
        try {
            String temp = ioService.getDefault();
            if(!temp.equals("")){
                try {
                    submit(temp);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(250, 250);
        setVisible(true);
        setResizable(false);
    }

    public void submit(String cityName) throws Exception {
        var weatherDto = httpRequestsService.getWeather(cityName);
        infoPanel = new InfoPanel(weatherDto, cityName);
        searchPanel.dropdownSuggestions.clearSelection();
        ioService.putLastSearch(cityName);
        searchPanel.dropdownSuggestions.setListData(ioService.getLastSearches());
        exchangeSearch();
        validate();
    }

    public void exchangeInfo() {
        remove(infoPanel);
        add(searchPanel);
        revalidate();
        repaint();
    }

    public void exchangeSearch() {
        remove(searchPanel);
        add(infoPanel);
        revalidate();
        repaint();
    }

}
