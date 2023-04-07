package com.example.meetup.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    @Column
    private String content;

    @ManyToOne
    private MeetEntity meet;

    @ManyToOne
    private UserEntity author;

    public CommentEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public CommentEntity setMeet(MeetEntity meet) {
        this.meet = meet;
        return this;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }
}
