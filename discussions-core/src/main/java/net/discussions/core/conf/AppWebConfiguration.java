package net.discussions.core.conf;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.discussions.core.business.PostBusiness;
import net.discussions.core.business.UserBusiness;
import net.discussions.core.controller.PostController;
import net.discussions.core.controller.UserController;
import net.discussions.core.dao.GenericDAO;
import net.discussions.core.dao.LocationDAO;
import net.discussions.core.dao.PostDAO;
import net.discussions.core.dao.UserDAO;

@EnableWebMvc
@ComponentScan(basePackageClasses = {
		UserDAO.class, PostDAO.class, 
		UserBusiness.class, PostBusiness.class,  
		UserController.class, PostController.class,
		LocationDAO.class, GenericDAO.class })
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
		b.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy,MM,dd,hh,mm,ss"));
		return b;
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

}
