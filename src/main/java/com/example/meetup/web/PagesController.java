package com.example.meetup.web;

import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.dto.views.PopularMeetView;
import com.example.meetup.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PagesController {

    private final MeetService meetService;

    @Autowired
    public PagesController(MeetService meetService) {
        this.meetService = meetService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<PopularMeetView> meets = this.meetService.getPopularMeets();

        model.addAttribute("meets", meets);

        return "index";
    }

    @GetMapping("/pages/all")
    public String all() {
        return "all";
    }

    @GetMapping("/pages/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/pages/moderators")
    public String moderators() {
        return "moderators";
    }

    @GetMapping("/users/logout")
    public String logout() {
        return "redirect:/";
    }
}
