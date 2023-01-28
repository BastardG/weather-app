package ru.bastard.weather;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.bastard.weather.configuration.BeanConfiguration;
import ru.bastard.weather.gui.SearchCityFrame;

@Component
public class Main {

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
    public static final SearchCityFrame searchCityFrame = (SearchCityFrame) context.getBean("searchFrame");

    public static void main(String[] args) {

    }

    /*
        TODO:
            2. Refactor
            3. Post to github
     */

}
