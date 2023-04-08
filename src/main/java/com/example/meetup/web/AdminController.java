package com.example.meetup.web;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.views.UserIndexView;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admins")
    public String getAdmins(Model model){
        List<UserIndexView> users = this.userService.getAllUsersIndexView();

        model.addAttribute("users", users);

        return "admins";
    }

    @GetMapping("/admins/add/{id}")
    public String makeAdmin(@PathVariable("id") Long userId){
        this.userService.makeAdmin(userId);

        return "redirect:/admins";
    }

    @GetMapping("/admins/remove/{id}")
    public String removeAdmin(@PathVariable("id") Long userId){
        this.userService.removeAdmin(userId);

        return "redirect:/admins";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView onObjectNotFound(ObjectNotFoundException onfe){
        ModelAndView model = new ModelAndView("object-not-found");

        model.addObject("objectId", onfe.getIdentifier());

        return model;
    }

}
