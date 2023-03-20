package com.example.meetup.web;

import com.example.meetup.domain.dto.binding.UserRegisterDTO;
import com.example.meetup.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController{

    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public RegisterController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/users/register")
    public String register() {
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String postRegister(UserRegisterDTO userRegisterDTO,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        userService.registerUser(userRegisterDTO, successfulAuth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);

            securityContextRepository.saveContext(context, request, response);
        });

        return "redirect:/";
    }
}
