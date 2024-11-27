package com.rbac.vrv.config;

import com.rbac.vrv.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable());
//        http.authorizeHttpRequests(request -> {
//            request.requestMatchers( "users").hasRole("ADMIN");
//            request.requestMatchers("register", "login", "admin").permitAll().anyRequest().authenticated();
//        });

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").hasRole("ADMIN") // Ensure role prefixing
                .requestMatchers("/register", "/login", "/admin").permitAll()
                .anyRequest().authenticated()
        );

//           http.authorizeHttpRequests(request ->  request.requestMatchers("register", "login").permitAll().anyRequest().authenticated());
//        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public UserDetailsService customUserDetailService() {
//        UserDetails user =  User.withDefaultPasswordEncoder().username("amit").password("123").roles("").build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    AuthenticationProvider customAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    AuthenticationManager customAuthenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
    }

}
