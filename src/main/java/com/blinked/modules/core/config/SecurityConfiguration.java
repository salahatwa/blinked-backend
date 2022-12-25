package com.blinked.modules.core.config;

import static com.blinked.modules.core.response.Responses.forbidden;
import static com.blinked.modules.core.utils.HttpContext.publicRoutes;
import static com.blinked.modules.core.utils.SecurityEnvironments.ACESSO_NEGADO_URL;
import static com.blinked.modules.core.utils.SecurityEnvironments.DAY_MILLISECONDS;
import static com.blinked.modules.core.utils.SecurityEnvironments.ENCODER;
import static com.blinked.modules.core.utils.SecurityEnvironments.HOME_URL;
import static com.blinked.modules.core.utils.SecurityEnvironments.LOGIN_ERROR_URL;
import static com.blinked.modules.core.utils.SecurityEnvironments.LOGIN_URL;
import static com.blinked.modules.core.utils.SecurityEnvironments.LOGOUT_URL;
import static com.blinked.modules.core.utils.SecurityEnvironments.PASSWORD_PARAMETER;
import static com.blinked.modules.core.utils.SecurityEnvironments.SESSION_COOKIE_NAME;
import static com.blinked.modules.core.utils.SecurityEnvironments.TOKEN_SECRET;
import static com.blinked.modules.core.utils.SecurityEnvironments.USERNAME_PARAMETER;
import static com.blinked.modules.core.utils.Strings.noneOfThenNullOrEmpty;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import com.blinked.modules.core.secuirty.AuthenticationMiddleware;
import com.blinked.modules.user.services.AuthenticationService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
  private final UserDetailsService userDetailsService;
  private final AuthenticationMiddleware authenticationMiddleware;

  public static String SWAGGER_USERNAME = null;
  public static String SWAGGER_PASSWORD = null;

  @Autowired
  public SecurityConfiguration(
      AuthenticationService authenticationService,
      AuthenticationMiddleware filter,
      @Value("${swagger.username}") String username,
      @Value("${swagger.password}") String password
  ) {
    this.userDetailsService = authenticationService;
    this.authenticationMiddleware = filter;
    
    SecurityConfiguration.SWAGGER_USERNAME = username;
    SecurityConfiguration.SWAGGER_PASSWORD = password;
  }

  @Autowired
  protected void globalConfiguration(AuthenticationManagerBuilder authentication) throws Exception {   
    if (noneOfThenNullOrEmpty(SWAGGER_PASSWORD, SWAGGER_USERNAME)) {
      authentication
        .inMemoryAuthentication()
          .passwordEncoder(ENCODER)
          .withUser(SWAGGER_USERNAME)
          .password(ENCODER.encode(SWAGGER_PASSWORD))
          .authorities(new ArrayList());
    }
    
    authentication
      .userDetailsService(userDetailsService)
      .passwordEncoder(ENCODER);
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }
  
  @Bean
  @Order(1)
  public SecurityFilterChain api(HttpSecurity http) throws Exception {
    publicRoutes()
      .add(GET, "/api")
      .add(POST, "/api/users", "/api/authentication/**", "/api/recoveries/**")
    .injectOn(http);

    http
      .antMatcher("/api/**")
        .authorizeRequests()
          .anyRequest()
            .authenticated()
      .and()
          .csrf()
          .disable()
            .exceptionHandling()
              .authenticationEntryPoint((request, response, exception) -> forbidden(response))
      .and()                
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
      .and()
        .addFilterBefore(authenticationMiddleware, UsernamePasswordAuthenticationFilter.class)
      .cors()
        .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain app(HttpSecurity http) throws Exception {
    http
      .antMatcher("/app/**")
        .authorizeRequests()
          .antMatchers(GET, LOGIN_URL, "/app", "/app/register", "/app/recovery/**")
            .permitAll()
          .antMatchers(POST, "/app/register", "/app/recovery/**")
            .permitAll()
          .anyRequest()
            .hasAuthority("USER")              
      .and()
        .csrf()
          .disable()                    
            .formLogin()
              .loginPage(LOGIN_URL)
                .failureUrl(LOGIN_ERROR_URL)
                .defaultSuccessUrl(HOME_URL)
              .usernameParameter(USERNAME_PARAMETER)
              .passwordParameter(PASSWORD_PARAMETER)
      .and()
        .rememberMe()                    
          .key(TOKEN_SECRET)
            .tokenValiditySeconds(DAY_MILLISECONDS)
      .and()
        .logout()
          .deleteCookies(SESSION_COOKIE_NAME)
            .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
            .logoutSuccessUrl(LOGIN_URL)
      .and()
        .exceptionHandling()
          .accessDeniedPage(ACESSO_NEGADO_URL);

    return http.build();
  }

  @Bean
  @Order(4)
  public SecurityFilterChain swagger(HttpSecurity http) throws Exception {
    if (noneOfThenNullOrEmpty(SWAGGER_PASSWORD, SWAGGER_USERNAME)) {
      http
        .antMatcher("/swagger-ui/**")
          .authorizeRequests()
            .anyRequest()
              .authenticated()
        .and()
          .sessionManagement()
            .sessionCreationPolicy(STATELESS)
        .and()
          .httpBasic();
    }
    return http.build();
  }
}