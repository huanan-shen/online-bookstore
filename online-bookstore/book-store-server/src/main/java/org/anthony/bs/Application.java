package org.anthony.bs;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Anthony
 */
@EnableDubbo(scanBasePackages = "org.anthony.bs")
@MapperScan(basePackages = {"org.anthony.bs.mapper"})
@PropertySource(value = {"classpath:/application.properties", "classpath:/log4j.properties", "classpath:/messages.properties"})
@EnableJpaAuditing
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    /**
     * app main start
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * tomcat start configure
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
