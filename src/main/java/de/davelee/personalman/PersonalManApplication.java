package de.davelee.personalman;

import de.davelee.personalman.gui.SplashScreen;
import de.davelee.personalman.gui.WelcomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties
public class PersonalManApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalManApplication.class);

    public static void main ( String[] args ) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch ( Exception e ) { }
        //Display splash screen to the user.
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PersonalManApplication.class);

        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);
        UserInterface ui = context.getBean(UserInterface.class);
        ui.determineLocale(ui.getLocaleLanguage());
        //Display splash screen to the user.
        SplashScreen ss = new SplashScreen(ui);
        try {
            Thread.sleep(2000);
        } catch ( InterruptedException ie ) {
            LOG.warn("Thread interrupted - continuing program without display", ie);
        }
        ss.dispose();
        String company = JOptionPane.showInputDialog(null, ui.getUserInterfaceMessages().getEmployeesCompanyMessage());
        if ( company == null || company.isEmpty()) {
            System.exit(0);
        }
        new WelcomeScreen(ui, company);
    }

}