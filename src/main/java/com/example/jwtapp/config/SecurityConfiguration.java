package com.example.jwtapp.config;

import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.Services.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService userService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //define username and password for form login authenication
        //here when giving password that password should be encorded
        auth.inMemoryAuthentication().withUser("Salitha").password(passwordEncorder().encode("Salitha")).authorities("USER","ADMIN");

        //Database Authentication
        auth.userDetailsService(userService).passwordEncoder(passwordEncorder());
    }

    @Bean
    public PasswordEncoder passwordEncorder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.authorizeRequests().anyRequest().permitAll();
        //In h2 console 403 error commit, this is its fixing
        //http.authorizeRequests((request) -> request.antMatchers("/h2-console/").permitAll().anyRequest().authenticated()).httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests((request) -> request.antMatchers("/h2-console/**").permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);


        http.cors();
        //http.formLogin();//form login added for authenication
        //http.httpBasic();//giving authentication for Basic Authentication


        //h2-console
        http.csrf().disable().headers().frameOptions().disable();//now h2 console will open
    }
}
