package com.heapminds.userservice.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
      securedEnabled = true,
      prePostEnabled = true
    )
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntrypoint jwtAuthEntrypoint;

    SecurityConfig(JwtAuthEntrypoint jwtAuthEntrypoint) {
        this.jwtAuthEntrypoint = jwtAuthEntrypoint;
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("I_RUN_SEC_AFTER_JWT_ENTRY");
        httpSecurity
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(jwtAuthEntrypoint)
        .and()
        .sessionManagement().
        sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
        authorizeRequests()
        .requestMatchers("/api/auth/**")
                .permitAll()
        .anyRequest().authenticated().and().httpBasic();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

}
