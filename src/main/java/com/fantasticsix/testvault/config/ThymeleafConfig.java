package com.fantasticsix.testvault.config;


import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {
    private final SpringResourceTemplateResolver templateResolver;

    public ThymeleafConfig(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.addDialect(new LayoutDialect());
        engine.addDialect(new SpringSecurityDialect()); // <- add both, layout first
        return engine;
    }
}