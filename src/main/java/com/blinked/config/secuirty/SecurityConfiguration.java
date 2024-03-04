package com.blinked.config.secuirty;

import static com.api.common.utils.Strings.noneOfThenNullOrEmpty;
import static com.blinked.config.secuirty.SecurityEnvironments.ENCODER;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.common.model.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	public static final String[] API_WHITELIST = { "/docs", "/actuator/**", "/v3/api-docs/**", "/swagger-ui.html",
			"/swagger-ui/**", "/api/users", "/api/authentication/**", "/api/recoveries/**" };

	@Autowired
	private CorsConfigurationSource customCores;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationMiddleware authenticationMiddleware;

	@Autowired
	private RestAuthenticationEntryPoint unauthorizedHandler;

	@Value("${swagger.username}")
	String username;
	public static String SWAGGER_USERNAME = null;

	@Value("${swagger.password}")
	public static String SWAGGER_PASSWORD = null;

	@Autowired
	protected void globalConfiguration(AuthenticationManagerBuilder authentication) throws Exception {
		if (noneOfThenNullOrEmpty(SWAGGER_PASSWORD, SWAGGER_USERNAME)) {
			authentication.inMemoryAuthentication().passwordEncoder(ENCODER).withUser(SWAGGER_USERNAME)
					.password(ENCODER.encode(SWAGGER_PASSWORD)).authorities(new ArrayList());
		}

		authentication.userDetailsService(userDetailsService).passwordEncoder(ENCODER);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain api(HttpSecurity http) throws Exception {
//		http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.csrf().disable().cors().configurationSource(customCores).and().exceptionHandling()
//				.authenticationEntryPoint(unauthorizedHandler).and().authorizeHttpRequests(
//						request -> request.requestMatchers(API_WHITELIST).permitAll().anyRequest().authenticated());
//
//		http.addFilterBefore(authenticationMiddleware, UsernamePasswordAuthenticationFilter.class);
////		
//		return http.build();

//		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//				.requestMatchers(API_WHITELIST).permitAll().anyRequest().authenticated();
//
////http.authenticationProvider((authenticationProvider()));
//		http.addFilterBefore(authenticationMiddleware, UsernamePasswordAuthenticationFilter.class);
//		return http.build();

		return http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
				.authenticationEntryPoint(authenticationEntryPoint()).and().cors().configurationSource(customCores)
				.and().csrf().disable().authorizeRequests().requestMatchers(API_WHITELIST).permitAll().anyRequest()
				.authenticated().and()
				.addFilterBefore(authenticationMiddleware, UsernamePasswordAuthenticationFilter.class).build();
	}

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return (httpServletRequest, httpServletResponse, e) -> {
			var error = new BaseResponse<>(401, "Not authenticated", null);

			var responseBody = new ObjectMapper().writeValueAsString(error);

			httpServletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
			httpServletResponse.getWriter().append(responseBody);
			httpServletResponse.setStatus(401);
		};
	}

	private AccessDeniedHandler accessDeniedHandler() {
		return (httpServletRequest, httpServletResponse, e) -> {
			var error = new BaseResponse<>(401, "Access denied", null);

			var responseBody = new ObjectMapper().writeValueAsString(error);

			httpServletResponse.getWriter().append(responseBody);
			httpServletResponse.setStatus(403);
			httpServletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
		};
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}