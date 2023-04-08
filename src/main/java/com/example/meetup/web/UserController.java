package com.example.meetup.web;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.models.UserModel;
import com.example.meetup.domain.dto.binding.UserEditDTO;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.dto.views.UserAccountView;
import com.example.meetup.service.MeetService;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final MeetService meetService;

    @Autowired
    public UserController(UserService userService, MeetService meetService) {
        this.userService = userService;
        this.meetService = meetService;
    }

    @GetMapping("/users/account/{username}")
    public String getAccount(@PathVariable("username") String username, Model model){
        UserModel user = this.userService.getUserByUsername(username);
        List<MeetIndexView> meets = this.meetService.getMeetsIndexViewByAnnouncerId(user.getId());

        UserAccountView userAccountView = new UserAccountView()
                .setId(user.getId())
                .setProfilePicUrl(user.getProfilePicture().getUrl())
                .setFullName(user.getFirstName() + " " + user.getLastName())
                .setMeetsAmnt(meets.size())
                .setMeets(meets);

        model.addAttribute("user", userAccountView);

        return "profile";
    }

    @GetMapping("/users/edit/{id}")
    public String getEditAccount(@PathVariable("id") Long userId, Model model){
        UserEditDTO userToEdit = this.userService.getUserToEdit(userId);

        model.addAttribute("userToEdit", userToEdit);

        return "account-edit";
    }

    @PostMapping("/users/edit/{id}")
    public String editAccount(@PathVariable("id") Long userId, @ModelAttribute(name = "userToEdit") UserEditDTO userToEdit,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userToEdit", userToEdit)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userToEdit"
                            ,bindingResult);

            return "redirect:/users/edit/{id}";
        }

        this.userService.saveEditedUser(userToEdit);

        return "redirect:/users/logout";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView onObjectNotFound(ObjectNotFoundException onfe){
        ModelAndView model = new ModelAndView("object-not-found");

        model.addObject("objectMessage", onfe.getMessage());

        return model;
    }

}
