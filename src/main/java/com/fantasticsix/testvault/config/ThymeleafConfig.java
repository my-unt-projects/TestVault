package com.fantasticsix.testvault.config;


import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver defaultTemplateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(defaultTemplateResolver); // Use default template resolver
        templateEngine.addDialect(new LayoutDialect()); // Add LayoutDialect for layout support
        return templateEngine;
    }
}