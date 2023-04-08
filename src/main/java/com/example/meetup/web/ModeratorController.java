package com.example.meetup.web;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.service.MeetService;
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
public class ModeratorController {

    private final MeetService meetService;
    private final UserService userService;

    @Autowired
    public ModeratorController(MeetService meetService, UserService userService) {
        this.meetService = meetService;
        this.userService = userService;
    }

    @GetMapping("/moderators")
    public String getModerators(Model model){
        List<MeetIndexView> meets = this.meetService.getAllMeets();

        model.addAttribute("meets", meets);

        return "moderators";
    }

    @GetMapping("/moderators/add/{id}")
    public String makeModerator(@PathVariable("id") Long userId){
        this.userService.makeModerator(userId);

        return "redirect:/moderators";
    }

    @GetMapping("/moderators/remove/{id}")
    public String removeModerator(@PathVariable("id") Long userId){
        this.userService.removeModerator(userId);

        return "redirect:/moderators";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView onObjectNotFound(ObjectNotFoundException onfe){
        ModelAndView model = new ModelAndView("object-not-found");

        model.addObject("objectId", onfe.getIdentifier());

        return model;
    }

}
