package com.example.meetup.web;

import com.example.meetup.domain.dto.views.PopularMeetView;
import com.example.meetup.service.MeetService;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PagesController {

    private final MeetService meetService;
    private final UserService userService;

    @Autowired
    public PagesController(MeetService meetService, UserService userService) {
        this.meetService = meetService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<PopularMeetView> meets = this.meetService.getPopularMeets();

        model.addAttribute("meets", meets);

        return "index";
    }

    @GetMapping("/users/logout")
    public String logout() {
        return "redirect:/";
    }

}
