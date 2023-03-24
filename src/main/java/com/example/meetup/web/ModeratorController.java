package com.example.meetup.web;

import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ModeratorController {

    private final MeetService meetService;

    @Autowired
    public ModeratorController(MeetService meetService) {
        this.meetService = meetService;
    }

    @GetMapping("/moderators")
    public String getModerators(Model model){
        List<MeetIndexView> meets = this.meetService.getAllMeets();

        model.addAttribute("meets", meets);

        return "moderators";
    }

}
