package com.example.meetup.web;

import com.example.meetup.domain.dto.views.UserIndexView;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/moderators/add/{id}")
    public String makeModerator(@PathVariable("id") Long userId){
        this.userService.makeModerator(userId);

        return "redirect:/admins";
    }

}
