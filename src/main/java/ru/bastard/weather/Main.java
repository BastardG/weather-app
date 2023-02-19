package ru.bastard.weather;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.bastard.weather.configuration.BeanConfiguration;
import ru.bastard.weather.gui.MainFrame;

@Component
public class Main {

    public static final int LANG = 1;
    public static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(BeanConfiguration.class);
    public static final MainFrame MAIN_FRAME = (MainFrame) CONTEXT.getBean("mainFrame");

    public static void main(String[] args) {}

}
