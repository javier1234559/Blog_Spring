package com.example.blog_springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests (auth -> {
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/","/about","/contact","/posts/**", "/register", "/error", "/admin/**","/uploaded/**").permitAll();
                    auth.requestMatchers("/templates/**","/static/**","/product/**","/dashboard/**").permitAll();
                    auth.requestMatchers("/api/posts/**","/api/posts").permitAll();
                    auth.anyRequest().authenticated();
                        })
//                .formLogin(form -> {
//                            form.usernameParameter("email");
//                            form.passwordParameter("pass");
//                            form.loginPage("/login");
//                            form.loginProcessingUrl("/login");
//                            form.defaultSuccessUrl("/");
//                            form.permitAll();
//                        })
                .formLogin();

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("hach")
                .password(encoder.encode("hacheery"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("nhat")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

}
