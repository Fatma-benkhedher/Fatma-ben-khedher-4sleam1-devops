package tn.esprit.studentmanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/student/actuator/**"))
                // Permit all requests to actuator endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/student/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Keep form login for other endpoints
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
