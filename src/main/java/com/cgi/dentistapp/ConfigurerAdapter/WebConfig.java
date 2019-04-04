package com.cgi.dentistapp.ConfigurerAdapter;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//https://memorynotfound.com/adding-static-resources-css-javascript-images-thymeleaf/
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    /**
     * some WebMvcConfigurerAdapter shit -> needed it for getting static js
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/img/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }
}
