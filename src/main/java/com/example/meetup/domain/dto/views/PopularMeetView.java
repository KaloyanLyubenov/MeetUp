package com.example.meetup.domain.dto.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PopularMeetView {
    private Long id;
    private String meetTitle;
    private String meetType;
    private String thumbnailUrl;

    public PopularMeetView setId(Long id) {
        this.id = id;
        return this;
    }

    public PopularMeetView setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public PopularMeetView setMeetType(String meetType) {
        this.meetType = meetType;
        return this;
    }

    public PopularMeetView setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }
}
