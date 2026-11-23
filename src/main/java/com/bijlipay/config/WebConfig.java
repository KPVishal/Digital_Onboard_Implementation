package com.bijlipay.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableWebMvc
@EnableSpringDataWebSupport
@Configuration
@ComponentScan("com.bijlipay")
public class WebConfig extends  WebMvcConfigurerAdapter  {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    public Pageable resolvePageable(Map<String, String> requestParams, Pageable pageable) {
        List<Sort.Order> orders=new ArrayList<>();
        Sort sorts = pageable.getSort();
        if(sorts !=null){
            sorts.forEach(sort ->{
                String propertyName =sort.getProperty();
                String dirKey= propertyName + ".dir";
                if (requestParams.containsKey(dirKey)){
                    String dirValue = requestParams.get(dirKey);
                    Sort.Direction direction=dirValue.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    orders.add(new Sort.Order(direction,propertyName));
                }
            });
        }
        return orders.isEmpty() ? pageable : PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by(orders));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        Hibernate5Module h5module = new Hibernate5Module();
        h5module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        h5module.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);

        for (HttpMessageConverter<?> mc : converters) {
            if (mc instanceof MappingJackson2HttpMessageConverter || mc instanceof MappingJackson2XmlHttpMessageConverter) {
                ((AbstractJackson2HttpMessageConverter) mc).getObjectMapper().registerModule(h5module);
            }
        }
        return;
    }
}
