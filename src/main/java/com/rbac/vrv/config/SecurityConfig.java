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

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/author", "/book").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/book/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/librarian").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/author").hasAnyRole("ADMIN", "LIBRARIAN")// Ensure role prefixing// Ensure role prefixing
                .requestMatchers(HttpMethod.POST, "/book/*/user/*").hasAnyRole("ADMIN", "LIBRARIAN")// Ensure role prefixing
                .requestMatchers(HttpMethod.GET, "/book/borrowed").hasAnyRole("ADMIN", "LIBRARIAN")// Ensure role prefixing
                .requestMatchers("/register", "/login", "/admin").permitAll()
                .anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


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

//    @Bean
//    GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        return new GrantedAuthorityDefaults(""); // Remove the "ROLE_" prefix
//    }
}
