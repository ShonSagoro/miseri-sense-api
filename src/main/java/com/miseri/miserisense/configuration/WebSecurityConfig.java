package com.miseri.miserisense.configuration;

import com.miseri.miserisense.configuration.security.filter.JWTAuthorizationFilter;
import com.miseri.miserisense.configuration.security.filter.UserAuthenticationFilter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.NonNull;


import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    private JWTAuthorizationFilter authorizationFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                        .allowedHeaders("*")
                        .exposedHeaders("*");
            }
        };
    }

    @Value("${miseri.security.allow-request}")
    private String[] allowedPaths;

    @Bean
    SecurityFilterChain web(HttpSecurity http,  AuthenticationManager authManager) throws Exception {

        UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authManager);
        authenticationFilter.setFilterProcessesUrl("/user/sign-in");

        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .exceptionHandling(Customizer.withDefaults())
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UserAuthenticationFilter.class)
                .authorizeHttpRequests((authorized) -> authorized
                        .requestMatchers(allowedPaths)
                        .permitAll().anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).build();
    }

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}

