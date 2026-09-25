package com.saferoad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                String role = auth.getAuthority();
                if ("ROLE_CITIZEN".equals(role)) {
                    response.sendRedirect("/citizen");
                    return;
                } else if ("ROLE_POLICE".equals(role)) {
                    response.sendRedirect("/dashboard");
                    return;
                } else if ("ROLE_TRANSPORT".equals(role)) {
                    response.sendRedirect("/transport");
                    return;
                } else if ("ROLE_HIGHWAYS".equals(role)) {
                    response.sendRedirect("/highways");
                    return;
                } else if ("ROLE_HEALTH".equals(role)) {
                    response.sendRedirect("/health");
                    return;
                } else if ("ROLE_INSURANCE".equals(role)) {
                    response.sendRedirect("/insurance");
                    return;
                }
            }
            response.sendRedirect("/dashboard");
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            String normUser = username.trim().toLowerCase();

            // 1. Admin login
            if ("admin".equals(normUser)) {
                return User.withUsername("admin")
                    .password("admin")
                    .roles("ADMIN")
                    .build();
            }

            // 2. Police Department login
            if ("police".equals(normUser) || "officer".equals(normUser) || normUser.startsWith("officer_") || normUser.startsWith("inspector_")) {
                return User.withUsername(username.trim())
                    .password("officer123")
                    .roles("POLICE")
                    .build();
            }

            // 3. Transport Department (RTO) login
            if ("transport".equals(normUser) || "rto".equals(normUser) || normUser.startsWith("transport_") || normUser.startsWith("rto_")) {
                return User.withUsername(username.trim())
                    .password("transport123")
                    .roles("TRANSPORT")
                    .build();
            }


            // 5. Health Department (EMS / Hospital) login
            if ("health".equals(normUser) || "hospital".equals(normUser) || "ems".equals(normUser) || normUser.startsWith("health_") || normUser.startsWith("doctor_")) {
                return User.withUsername(username.trim())
                    .password("health123")
                    .roles("HEALTH")
                    .build();
            }

            // 6. Insurance Agency login
            if ("insurance".equals(normUser) || "claims".equals(normUser) || normUser.startsWith("insurance_") || normUser.startsWith("adjuster_")) {
                return User.withUsername(username.trim())
                    .password("insurance123")
                    .roles("INSURANCE")
                    .build();
            }

            // 7. Citizen / Vehicle Number login (e.g. KA-01-AB-1234, citizen)
            if (!normUser.isEmpty()) {
                return new UserDetails() {
                    @Override public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
                        return java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_CITIZEN"));
                    }
                    @Override public String getPassword() {
                        return "ANY_PHONE_ACCEPTED";
                    }
                    @Override public String getUsername() { return username.trim(); }
                    @Override public boolean isAccountNonExpired() { return true; }
                    @Override public boolean isAccountNonLocked() { return true; }
                    @Override public boolean isCredentialsNonExpired() { return true; }
                    @Override public boolean isEnabled() { return true; }
                };
            }

            throw new UsernameNotFoundException("User not found: " + username);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if ("ANY_PHONE_ACCEPTED".equals(encodedPassword)) {
                    // Citizen phone verification (must be at least 4 digits/characters)
                    return rawPassword != null && rawPassword.length() >= 4;
                }
                // Allow "police123" for police as well
                if ("officer123".equals(encodedPassword) && "police123".equals(rawPassword.toString())) {
                    return true;
                }
                return encodedPassword.equals(rawPassword.toString());
            }
        };
    }
}
