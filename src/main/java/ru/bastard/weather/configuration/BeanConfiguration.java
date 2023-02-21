package ru.bastard.weather.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import ru.bastard.weather.gui.MainFrame;
import ru.bastard.weather.service.http.HttpRequestsService;
import ru.bastard.weather.service.io.IOService;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@Configuration
public class BeanConfiguration {

    @Bean
    public static IOService ioService() {
        return new IOService();
    }

    @Bean
    public static Logger logger(){
        return Logger.getLogger("application");
    }

    @Bean
    public static RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean(value = "mainFrame", initMethod = "initialized")
    @Scope("singleton")
    public static MainFrame mainFrame(){
        MainFrame mainFrame = new MainFrame();
        return mainFrame;
    }

    @Bean
    public static HttpRequestsService mainService(){
        return new HttpRequestsService(restTemplate());
    }

    @Bean
    @Qualifier("lang")
    public static ResourceBundle language(@Qualifier("lang_code") String langCode) {
        switch (langCode) {
            case "ru" -> {
                return ResourceBundle.getBundle("lang", new Locale("ru", "RU"));
            }
            default -> {
                return ResourceBundle.getBundle("lang");
            }
        }
    }

    @Bean
    @Qualifier("lang_code")
    public static String langCode() {
        return ioService().getLang();
    }

}
