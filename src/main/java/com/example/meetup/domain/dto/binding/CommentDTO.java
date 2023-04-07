package com.example.meetup.domain.dto.binding;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentDTO {

    private String content;

    public CommentDTO setContent(String content) {
        this.content = content;
        return this;
    }
}
