package com.salesianostriana.dam.movingprobeta.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/css/**", "/js/**").permitAll()
				.requestMatchers("/mudanza/delete/**", "/vehiculo/delete/**", "/operario/delete/**",
						"/mudanzavehiculo/delete/**")
				.hasRole("ADMIN").requestMatchers("/mudanza/edit/**", "/vehiculo/edit/**", "/operario/edit/**")
				.hasRole("ADMIN").requestMatchers("/operario/new", "/operario/save").hasRole("ADMIN").anyRequest()
				.authenticated()).requestCache(cache -> {
					HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
					requestCache.setMatchingRequestParameterName(null);
					cache.requestCache(requestCache);
				}).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll());

		return http.build();
	}
}