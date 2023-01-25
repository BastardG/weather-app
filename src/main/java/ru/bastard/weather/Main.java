package ru.bastard.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bastard.weather.configuration.BeanConfiguration;
import ru.bastard.weather.gui.SearchCityFrame;
import ru.bastard.weather.service.http.HttpRequestsService;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Component
public class Main {

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
    private static SearchCityFrame searchCityFrame = (SearchCityFrame) context.getBean("searchFrame");


    @Autowired
    public Main() {

    }

    public static void main(String[] args) {

    }

}
