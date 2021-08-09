package com.bdular.inventorytracker.configurations.security;

import com.bdular.inventorytracker.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailService service;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder());
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(encoder().encode("admin"))
                .roles("ADMIN");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests().antMatchers("/").permitAll()
                .and().authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/swagger-ui/**").permitAll()
                .and().authorizeRequests().antMatchers("/index").permitAll()
                .and().authorizeRequests().antMatchers("/login").permitAll()
                .and().authorizeRequests().antMatchers("/auth/**").permitAll();
                //.anyRequest().authenticated();
        /* http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);*/
    }

}
