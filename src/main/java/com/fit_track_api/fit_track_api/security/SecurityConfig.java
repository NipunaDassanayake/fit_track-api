package com.fit_track_api.fit_track_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // or Pbkdf2PasswordEncoder or another implementation
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login/**",
                                "/oauth2/**",
                                "/api/users/**",
                                "/api/workoutPost/**",
                                "/api/achievements/**",
                                "/api/comment/**",
                                "/api/questions/**",
                                "/api/workoutPlan/**"

                        ).permitAll()  // Public endpoints
                        .anyRequest().authenticated()  // Secure other endpoints
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login/google")
                        .defaultSuccessUrl("/loginSuccess", true)
                        .failureUrl("/loginFailure")

                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define the logout URL
                        .logoutSuccessUrl("/api/users/login") // Redirect to homepage after logout
                        .invalidateHttpSession(true) // Invalidate the session on logout
                        .clearAuthentication(true) // Clear authentication information
                        .deleteCookies("JSESSIONID") // Delete the JSESSIONID cookie after logout
                        .permitAll()); // Allow everyone to access logout functionality

        return httpSecurity.build();
    }
}



