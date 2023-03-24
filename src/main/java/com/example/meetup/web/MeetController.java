package com.example.meetup.web;

import com.example.meetup.domain.dto.MeetModel;
import com.example.meetup.domain.dto.MeetTypeModel;
import com.example.meetup.domain.dto.VehicleTypeModel;
import com.example.meetup.domain.dto.binding.AddMeetDTO;
import com.example.meetup.domain.dto.binding.AddPictureDTO;
import com.example.meetup.domain.dto.binding.EditMeetDTO;
import com.example.meetup.domain.dto.views.MeetDetailsView;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import com.example.meetup.service.MeetService;
import com.example.meetup.service.PictureService;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/meets")
public class MeetController {

    public final MeetService meetService;
    private final PictureService pictureService;
    private final UserService userService;

    @Autowired
    public MeetController(MeetService meetService, PictureService pictureService, UserService userService) {
        this.meetService = meetService;
        this.pictureService = pictureService;
        this.userService = userService;
    }


    @GetMapping("/add")
    public String getAddMeet(@ModelAttribute(name = "addMeetModel") AddMeetDTO addMeetDTO){
        return "meet-add";
    }

    @PostMapping("/add")
    public String postAddMeet(@ModelAttribute(name = "addMeetModel") AddMeetDTO addMeetDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addMeetModel", addMeetDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addMeetModel"
                    ,bindingResult);

            return "redirect:/meets/add";
        }

        this.meetService.addMeet(addMeetDTO);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String getAllMeets(Model model){
        List<MeetIndexView> meets = this.meetService.getAllMeets();

        model.addAttribute("meets", meets);

        return "meets-view-all";
    }

    @GetMapping("/details/{id}")
    public String getMeetDetails(@PathVariable("id") Long meetId, Model model, AddPictureDTO addPictureDTO){
        MeetDetailsView meet = this.meetService.getMeetDetails(meetId);

        addPictureDTO.setIdOfMeet(meetId);

        model.addAttribute("meet", meet);
        model.addAttribute("addPictureDTO", addPictureDTO);

        return "meet-details";
    }

    @PostMapping("/details/add-image/{id}")
    public String addPicture(@PathVariable("id") Long meetId, AddPictureDTO addPictureDTO){
        this.pictureService.addPicture(addPictureDTO.setIdOfMeet(meetId));

        return "redirect:/meets/details/" + meetId;

    }
    @GetMapping("/edit/{id}")
    public String getEditMeet(@PathVariable("id") Long meetId, Model model){

        EditMeetDTO editMeetDTO = this.meetService.findMeetToEditById(meetId);

        model.addAttribute("editMeetDTO", editMeetDTO);

        return "meet-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMeet(@PathVariable("id") Long meetId, @ModelAttribute(name = "editMeetModel") EditMeetDTO editMeetDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("editMeetDTO", editMeetDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addMeetModel"
                            ,bindingResult);

            return "redirect:/meets/edit/{id}";
        }

        this.meetService.editMeet(editMeetDTO);

        return "redirect:/";
    }

}
