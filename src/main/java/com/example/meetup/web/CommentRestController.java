package com.example.meetup.web;

import com.example.meetup.domain.dto.views.CommentView;
import com.example.meetup.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentRestController {
    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/{meetId}/comments")
    public ResponseEntity<List<CommentView>> getCommentsMeets(@PathVariable("meetId") Long meetId){
        List<CommentView> comments = this.commentService.getCommentsByMeetId(meetId)
                .stream().map(comment -> new CommentView()
                        .setId(comment.getId())
                        .setContent(comment.getContent())
                        .setAuthorUsername(comment.getAuthor().getUsername())).toList();

        return ResponseEntity.ok(comments);
    }

}
