package com.example.meetup.web;

import com.example.meetup.domain.dto.binding.AddMeetModel;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/meets")
public class MeetController {

    public final MeetService meetService;

    @Autowired
    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }


    @GetMapping("/add")
    public String getAddMeet(@ModelAttribute(name = "addMeetModel")AddMeetModel addMeetModel){
        return "meet-add";
    }

    @PostMapping("/add")
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

    @GetMapping("/all")
    public String getAllMeets(Model model){
        List<MeetIndexView> meets = meetService.getAllMeets();

        model.addAttribute("meets", meets);

        return "meet-view-all";
    }

    @ModelAttribute(name = "addMeetModel")
    public AddMeetModel addMeetModel(){
        return new AddMeetModel();
    }

}
