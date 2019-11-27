package org.learn.controller.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    //
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("rolesauthorities/home");
		registry.addViewController("/protectedbynothing").setViewName("rolesauthorities/protectedbynothing");
		registry.addViewController("/protectedbyrole").setViewName("rolesauthorities/protectedbyrole");
		registry.addViewController("/protectedbyadminrole").setViewName("rolesauthorities/protectedbyadminrole");
		registry.addViewController("/protectedbyuserrole").setViewName("rolesauthorities/protectedbyuser");
		registry.addViewController("/login").setViewName("rolesauthorities/login");
		registry.addViewController("/home").setViewName("rolesauthorities/home");
		registry.addViewController("/403").setViewName("rolesauthorities/403");
		registry.addViewController("/logout");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
	/*
	 * @Bean public InternalResourceViewResolver jspViewResolver() {
	 * InternalResourceViewResolver resolver= new InternalResourceViewResolver();
	 * resolver.setPrefix("/templates/"); resolver.setSuffix(".html"); return
	 * resolver; }
	 */  
}