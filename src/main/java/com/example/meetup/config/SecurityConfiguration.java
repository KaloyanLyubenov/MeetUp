package com.example.meetup.config;

import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                // defines which pages will be authorized
                authorizeHttpRequests().
                    // allow access to all static files (images, CSS, js)
                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                    // the URL-s below are available for all users - logged in and anonymous
                    requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll().
                    // only for moderators
                    requestMatchers("/panel/moderator").hasRole(UserRoleEnum.MODERATOR.name()).
                    // only for admins
                    requestMatchers("/panel/admin").hasRole((UserRoleEnum.ADMIN.name())).
                anyRequest().authenticated().
                    and().
                        // configure login with HTML form
                        formLogin().
                            loginPage("/users/login").
                            // the names of the username, password input fields in the custom login form
                            usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                            passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                            // where do we go after login
                            defaultSuccessUrl("/home").
                            failureForwardUrl("users/login-error").
                    and().logout().logoutUrl("/users/logout").
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new ApplicationUserDetailsService(userRepository);
    }

}
