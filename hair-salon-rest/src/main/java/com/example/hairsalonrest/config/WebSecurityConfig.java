package com.example.hairsalonrest.config;

import com.example.hairsalonrest.security.CurrentUserDetailServiceImpl;
import com.example.hairsalonrest.security.JWTAuthenticationTokenFilter;
import com.example.hairsalonrest.security.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CurrentUserDetailServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/users").authenticated()
                .antMatchers(HttpMethod.GET, "/feedbacks", "/orders", "/photos", "/services", "/workers",
                        "/users/email", "/feedbacks/id", "/orders/*", "/workers/*").permitAll()
                .antMatchers(HttpMethod.POST, "/users", "/services", "/workers").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/feedbacks", "/users/active", "/feedbacks/*", "/orders",
                        "/photos/*").authenticated()
                .antMatchers(HttpMethod.POST, "/users/auth").permitAll()
                .antMatchers(HttpMethod.PUT, "/feedbacks/*", "/orders/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/workers/*", "/services/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/users").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/{id}", "/services/*", "/workers/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/feedbacks", "/orders/*", "/photos/*").authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationTokenFilter();
    }

}