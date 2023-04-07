package com.example.meetup.domain.dto.binding;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDTO {

    private Long id;
    private Long meetId;
    private Long authorId;
    private String content;

    public AddCommentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public AddCommentDTO setMeetId(Long meetId) {
        this.meetId = meetId;
        return this;
    }

    public AddCommentDTO setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public AddCommentDTO setContent(String content) {
        this.content = content;
        return this;
    }
}
