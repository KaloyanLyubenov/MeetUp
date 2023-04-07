package com.example.meetup.web;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.binding.AddCommentDTO;
import com.example.meetup.repository.MeetRepository;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.service.CommentService;
import com.example.meetup.service.MeetService;
import com.example.meetup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class CommentsController {

    private final UserService userService;
    private final CommentService commentService;
    private final MeetService meetService;

    @Autowired
    public CommentsController(UserRepository userRepository, UserService userService, CommentService commentService, MeetService meetService){
        this.userService = userService;
        this.commentService = commentService;
        this.meetService = meetService;
    }

    @PostMapping("/comments/add/{id}")
    public String addComment(@PathVariable("id") Long meetId, Principal principal, AddCommentDTO addCommentDTO){
        addCommentDTO
                .setAuthorId(this.userService.getUserByUsername(principal.getName()).getId())
                .setMeetId(meetId);

        this.commentService.saveComment(addCommentDTO);

        addCommentDTO.setId(this.commentService.getCommentIdByContent(addCommentDTO.getContent()));

        this.meetService.addComment(addCommentDTO);

        return "redirect:/meets/comments/" + meetId;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView onObjectNotFound(ObjectNotFoundException onfe){
        ModelAndView model = new ModelAndView("object-not-found");

        model.addObject("objectId", onfe.getIdentifier());

        return model;
    }
}
