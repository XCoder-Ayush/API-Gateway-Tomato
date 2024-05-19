package com.backend.tomato.config;

import com.backend.tomato.security.JwtAuthenticationEntryPoint;
import com.backend.tomato.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers("/cloudinary/upload").permitAll()
                .requestMatchers("/api/payments/hook").permitAll()

                .requestMatchers("/health").permitAll()
                .requestMatchers("/category").permitAll()
                .requestMatchers("/category/{categoryId}").permitAll()
                .requestMatchers("/food/get").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/createuser").permitAll()
                .requestMatchers("/auth/verifyotp").permitAll()
                .requestMatchers("/food/add").permitAll()
                .requestMatchers("/food/update").permitAll()
                .requestMatchers("/check/admin").hasAuthority("ADMIN")
                .requestMatchers("/check/user").hasAuthority("USER")
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
/*
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
*/
}
