package com.example.meetup.domain.dto;

import com.example.meetup.domain.entities.MeetEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PictureModel {
    private Long id;
    private String title;
    private String url;

    public PictureModel setId(Long id) {
        this.id = id;
        return this;
    }

    public PictureModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public PictureModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
