package com.alkemy.challenge;

import com.alkemy.challenge.filter.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {//TODO implement auth for required methods
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/genres").permitAll()
					.antMatchers(HttpMethod.GET, "/genres").permitAll()
					.antMatchers(HttpMethod.POST, "/movies").permitAll()
					.antMatchers(HttpMethod.GET, "/movies").permitAll()
					.antMatchers(HttpMethod.POST, "/characters").permitAll()
					.antMatchers(HttpMethod.GET, "/characters").permitAll()
					.anyRequest().authenticated();
		}
	}

}
