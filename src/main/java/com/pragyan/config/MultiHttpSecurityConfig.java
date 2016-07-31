package com.pragyan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.pragyan.security.CustomUserDetailsService;

@EnableWebSecurity
public class MultiHttpSecurityConfig {


	@Autowired
	private CustomUserDetailsService customUserDetailsService;



	@Configuration
	@Order(1)                                                        
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		private RestAuthenticationEntryPoint restEntryPoint;
		protected void configure(HttpSecurity http) throws Exception {
			http
			.antMatcher("/api/**")
			.authorizeRequests()
				.anyRequest().hasRole("USER")
				.and()
			.httpBasic().authenticationEntryPoint(restEntryPoint);

			http.csrf().disable();
		}
	}

	@Configuration                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests()
			.antMatchers("/" , "/signup").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/profile")
			.and()
			.logout().permitAll();

			http.csrf().disable();
		}
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.inMemoryAuthentication()
		.withUser("pragyan").password("pragyan").roles("USER");
	}


	/*	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}*/


	@SuppressWarnings("deprecation")
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordEncoder(){
		return new Md5PasswordEncoder();
	}

}
