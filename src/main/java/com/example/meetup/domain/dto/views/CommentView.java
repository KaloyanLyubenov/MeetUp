package com.example.meetup.domain.dto.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentView {

    private Long id;
    private String content;
    private String authorUsername;

    public CommentView setId(Long id) {
        this.id = id;
        return this;
    }

    public CommentView setContent(String content) {
        this.content = content;
        return this;
    }

    public CommentView setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }
}
