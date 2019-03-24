package com.eggplant.emoji.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private PasswordEncoderConfig passwordEncoderConfig;

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(passwordEncoderConfig.passwordEncoder().encode("user1Pass")).roles("USER")
//                .and()
//                .withUser("user2").password(passwordEncoderConfig.passwordEncoder().encode("user2Pass")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoderConfig.passwordEncoder().encode("adminPass")).roles("ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/userLogin.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home.html", true)
                .failureUrl("/login.html?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll();

//                http

//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/anonymous*").anonymous()
//                .antMatchers("/login*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/homepage.html", true)
//                //.failureUrl("/login.html?error=true")
//                .failureHandler(authenticationFailureHandler())
//                .and()
//                .logout()
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler());
    }

}
