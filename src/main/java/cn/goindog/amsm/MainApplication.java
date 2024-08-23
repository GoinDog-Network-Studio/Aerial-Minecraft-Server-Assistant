package cn.goindog.amsm;

import cn.goindog.amsm.utils.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer(){
        return new TomcatServletWebServerFactory(PropertiesUtils.server_port) ;
    }

}
