package com.luminor.task.payment;

import com.luminor.task.payment.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO make this hell test ? // https://en.wikipedia.org/wiki/Law_of_Demeter || only will cover if it will be deciding factor
        //for real code would make coverage for sure as this is primary security configurations, it's very important.
        //philosophy to cover this with tests need to make many studies why > in future there are chances that I won't work with spring security and this information could consume tons of time.
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/resources/**", "/registration").permitAll()
            .antMatchers("/rest-api/**").hasAuthority("RESTApi")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and().httpBasic();

    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

//    @Bean
//    public UserDetailsService getUserDetailsService() {
//        return this.getApplicationContext().getBean(UserDetailsServiceImpl.class);
//    }
}
