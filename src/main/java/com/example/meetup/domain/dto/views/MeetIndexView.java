package com.example.meetup.domain.dto.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MeetIndexView {

    private Long id;
    private String meetTitle;
    private String description;
    private Date date;
    private String thumbnailUrl;

    public MeetIndexView setDate(Date date) {
        this.date = date;
        return this;
    }

    public MeetIndexView setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }



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
