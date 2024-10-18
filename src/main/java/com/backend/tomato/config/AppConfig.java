package com.backend.tomato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class AppConfig {
/*
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("service1", r -> r.path("/service1/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://SERVICE-NAME1"))
				.build();
	}

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("http://localhost:8081/api/v1/orders")
                .route(RequestPredicates.path("/api/v1/orders"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }
*/

/*
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails= User.builder().username("ayush").password(passwordEncoder().encode("abc")).roles("ADMIN").build();

        UserDetailsService -> UserDetailsManager -> InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(userDetails);
    }
*/

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
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}