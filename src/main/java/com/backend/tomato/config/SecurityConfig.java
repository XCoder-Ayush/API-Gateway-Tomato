package com.backend.tomato.config;

import com.backend.tomato.security.JwtAuthenticationEntryPoint;
import com.backend.tomato.security.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger= LoggerFactory.getLogger(SecurityConfig.class);

//    @Autowired
//    @Qualifier("corsConfigurationSource")
//    private CorsConfigurationSource corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers("/check").permitAll()
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

//        http.oauth2Login(oauth ->{
//            oauth.loginPage("http://localhost:42000/auth");
//            oauth.successHandler(new AuthenticationSuccessHandler() {
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                    DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//                    redirectStrategy.setContextRelative(false);
//
//                    DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();
//                    user.getAttributes().forEach((key,value)->{
//                        logger.info("{} -> {}",key,value);
//                    });
//                    Cookie cookie = new Cookie("myCookie", "cookieValue");
//                    cookie.setHttpOnly(true);
//                    response.addCookie(cookie);
//                    redirectStrategy.sendRedirect(request,response,"http://localhost:42000/dashboard");
//                }
//            });
//        })
//                .formLogin().disable()
//                .httpBasic().disable();

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

}