package com.example.hairsalonrest.config;

import com.example.hairsalonrest.security.CurrentUserDetailServiceImpl;
import com.example.hairsalonrest.security.JWTAuthenticationTokenFilter;
import com.example.hairsalonrest.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                //user
                .antMatchers(HttpMethod.GET, "/users").authenticated()
                .antMatchers(HttpMethod.GET, "/users/email").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyAuthority("ADMIN")
                .antMatchers("/users/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/users").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/active").authenticated()
                .antMatchers(HttpMethod.PATCH, "/users").permitAll()
                //feedback
                .antMatchers(HttpMethod.GET, "/feedbacks").permitAll()
                .antMatchers(HttpMethod.GET, "/feedbacks/id").permitAll()
                .antMatchers(HttpMethod.POST, "/feedbacks").authenticated()
                .antMatchers(HttpMethod.POST, "/feedbacks/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/feedbacks").authenticated()
                .antMatchers(HttpMethod.PUT, "/feedbacks/*").authenticated()
                //order
                .antMatchers(HttpMethod.GET, "/orders").permitAll()
                .antMatchers(HttpMethod.GET, "/orders/*").permitAll()
                .antMatchers(HttpMethod.POST, "/orders").authenticated()
                .antMatchers(HttpMethod.PUT, "/orders/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/orders/*").authenticated()
                //photo
                .antMatchers(HttpMethod.GET, "/photos").permitAll()
                .antMatchers(HttpMethod.POST, "/photos/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/photos/*").authenticated()
                //Service
                .antMatchers(HttpMethod.GET, "/services").permitAll()
                .antMatchers(HttpMethod.POST, "/services").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/services/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/services/*").hasAnyAuthority("ADMIN")
                //Worker
                .antMatchers(HttpMethod.GET, "/workers").permitAll()
                .antMatchers(HttpMethod.GET, "/workers/*").permitAll()
                .antMatchers(HttpMethod.POST, "/workers").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/workers/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/workers/*").hasAnyAuthority("ADMIN");


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