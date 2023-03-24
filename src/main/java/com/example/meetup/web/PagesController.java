package com.example.meetup.web;

import com.example.meetup.domain.dto.UserModel;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.dto.views.PopularMeetView;
import com.example.meetup.domain.dto.views.UserAccountView;
import com.example.meetup.service.MeetService;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/users/account/{id}")
    public String getAccount(@PathVariable("id") Long userId, Model model){
        UserModel user = this.userService.getUserById(userId);
        List<MeetIndexView> meets = this.meetService.getMeetsIndexViewByAnnouncerId(userId);

        UserAccountView userAccountView = new UserAccountView()
                .setId(user.getId())
                .setProfilePicUrl(user.getProfilePicture().getUrl())
                .setFullName(user.getFirstName() + " " + user.getLastName())
                .setMeetsAmnt(meets.size())
                .setMeets(meets);

        model.addAttribute("user", userAccountView);

        return "profile";
    }
}
