package com.lestonpoh.financeapp_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator<HttpMessageConverter<?>> it = converters.iterator();
        while (it.hasNext()) {
            HttpMessageConverter<?> converter = it.next();
            if (converter instanceof MappingJackson2XmlHttpMessageConverter) {
                it.remove(); // remove XML converter
            }
        }
    }
}