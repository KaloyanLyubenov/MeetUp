package com.example.meetup.config;

import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.service.ApplicationUserDetailsService;
import jakarta.servlet.MultipartConfigElement;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.unit.DataSize;

import java.sql.PseudoColumnUsage;

@Configuration
public class SecurityConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           SecurityContextRepository securityContextRepository) throws Exception {
        http.
                // defines which pages will be authorized
                        authorizeHttpRequests().
                // allow access to all static files (images, CSS, js)
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                // the URL-s below are available for all users - logged in and anonymous
                        requestMatchers("/", "/users/login", "/users/register", "/users/login-error", "/meets/all", "/details/{id}").permitAll().
                // only for moderators
                        requestMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name()).
                // only for admins
                        requestMatchers("/pages/admins").hasRole(UserRoleEnum.ADMIN.name()).
                anyRequest().authenticated().
                and().
                // configure login with HTML form
                        formLogin().
                loginPage("/users/login").
                // the names of the username, password input fields in the custom login form
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                // where do we go after login
                        defaultSuccessUrl("/", true).//use true argument if you always want to go there, otherwise go to previous page
                failureForwardUrl("/users/login-error").
                and().logout().//configure logout
                logoutUrl("/users/logout").
                logoutSuccessUrl("/").//go to homepage after logout
                invalidateHttpSession(true).
                and().
                securityContext().
                securityContextRepository(securityContextRepository);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("20MB"));
        factory.setMaxRequestSize(DataSize.parse("20MB"));
        return factory.createMultipartConfig();
    }

}
