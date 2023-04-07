package com.example.meetup.domain.dto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentModel {
    private Long id;
    private String content;
    private MeetModel meet;
    private UserModel author;

    public CommentModel setId(Long id) {
        this.id = id;
        return this;
    }

    public CommentModel setContent(String content) {
        this.content = content;
        return this;
    }

    public CommentModel setMeet(MeetModel meet) {
        this.meet = meet;
        return this;
    }

    public CommentModel setAuthor(UserModel author) {
        this.author = author;
        return this;
    }
}
