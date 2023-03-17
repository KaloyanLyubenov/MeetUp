package com.example.meetup.web;

import com.example.meetup.domain.dto.binding.AddMeetModel;
import com.example.meetup.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetController {

    public final MeetService meetService;

    @Autowired
    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }


    @GetMapping("/meets/add")
    public String getAddMeet(@ModelAttribute(name = "addMeetModel")AddMeetModel addMeetModel){
        return "add-meet";
    }

    @PostMapping("/meets/add")
    public String postAddMeet(@ModelAttribute(name = "addMeetModel")AddMeetModel addMeetModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addMeetModel", addMeetModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addMeetModel"
                    ,bindingResult);

            return "redirect:/meets/add";
        }

        this.meetService.addMeet(addMeetModel);

        return "redirect:/";
    }

    @ModelAttribute(name = "addMeetModel")
    public AddMeetModel addMeetModel(){
        return new AddMeetModel();
    }

}
