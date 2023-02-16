package ru.bastard.weather.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import ru.bastard.weather.gui.MainFrame;
import ru.bastard.weather.service.http.HttpRequestsService;

import java.util.logging.Logger;

@Configuration
public class BeanConfiguration {

    @Bean
    public static Logger logger(){
        return Logger.getLogger("application");
    }

    @Bean
    public static RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean("mainFrame")
    @Scope("singleton")
    public static MainFrame mainFrame(){
        return new MainFrame();
    }

    @Bean
    public static HttpRequestsService mainService(){
        return new HttpRequestsService(restTemplate());
    }

}
