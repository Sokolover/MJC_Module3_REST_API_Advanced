package com.epam.esm.sokolov.config;

import com.epam.esm.sokolov.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity//todo check if i need it
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html"
    };

    //    @Qualifier("userDetailsServiceImpl")
//    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, "/api/orders").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.PATCH, "/api/gift-certificates/{id}").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/users/{id}/orders").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.GET, "/api/users/{id}/orders/{orderId}").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyRole(USER, ADMIN)
//                .antMatchers(HttpMethod.POST, "/api/orders").permitAll()
//                .antMatchers(HttpMethod.PATCH, "/api/gift-certificates/{id}").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/users/{id}/orders").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/users/{id}/orders/{orderId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/gift-certificates").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tags").permitAll()
                .antMatchers("/api/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
