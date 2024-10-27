package com.example.User_Service_Jwt.config;

import com.example.User_Service_Jwt.filter.JwtAuthFilter;
import com.example.User_Service_Jwt.service.impl.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;
    /*@Bean
    //authentication
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("Radhakant")
                .password(encoder.encode("Pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("Radhakant")
                .password(encoder.encode("Pwd2"))
                .roles("USER","HR")
                .build();
        return new InMemoryUserDetailsManager(admin, user);

    }*/
    @Bean

    public UserDetailsService userDetailsService() {

        return new UserInfoUserDetailsService();
    }
       /* @Bean
        public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())  // Use lambda to customize CSRF
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/employees/create","/api/users/create","/api/users/authenticate").permitAll()
                            .requestMatchers("/api/employees/**").authenticated()
                    )
                    .formLogin(Customizer.withDefaults())  // Simplified form login
                    .build();
        }*/
       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .csrf(csrf -> csrf.disable()) // Disable CSRF
                   .authorizeHttpRequests(auth -> auth
                           .requestMatchers("/api/employees/create", "/api/users/create", "/api/users/authenticate").permitAll()
                           .requestMatchers("/api/employees/**").authenticated()
                   )
                   .sessionManagement(session -> session
                           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   )
                   .authenticationProvider(authenticationProvider()) // Custom authentication provider
                   .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // Add custom filter
                   .build();
       }


    @Bean
        public PasswordEncoder passwordEncoder () {
            return new BCryptPasswordEncoder();
        }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}

