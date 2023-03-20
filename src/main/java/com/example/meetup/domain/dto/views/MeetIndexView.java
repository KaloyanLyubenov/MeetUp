package com.example.meetup.domain.dto.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MeetIndexView {

    private Long id;
    private String meetTitle;
    private String description;

    public MeetIndexView setId(Long id) {
        this.id = id;
        return this;
    }

    public MeetIndexView setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public MeetIndexView setDescription(String description) {
        this.description = description;
        return this;
    }
}
