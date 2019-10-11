package com.ellis.cmfz.config;

import com.ellis.cmfz.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
@Configuration
public class AdminFilterConfig {
    @Bean
    public FilterRegistrationBean AdminFilter(){
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/jsp/*");
        registrationBean.addUrlPatterns("/banner/*");
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.addUrlPatterns("/article/*");
        registrationBean.addUrlPatterns("/album/*");
        registrationBean.addUrlPatterns("/chapter/*");
        registrationBean.setName("AdminFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
