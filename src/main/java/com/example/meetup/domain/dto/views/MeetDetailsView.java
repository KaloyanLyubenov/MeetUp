package com.example.meetup.domain.dto.views;

import com.example.meetup.domain.entities.MeetTypeEntity;
import com.example.meetup.domain.entities.PictureEntity;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.entities.VehicleTypeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MeetDetailsView {
    private String meetTitle;
    private String meetType;
    private String vehicleType;
    private String description;
    private Date date;
    private String announcer;
    private List<String> pictureUrls = new ArrayList<>();
    private String thumbnailUrl;


    public MeetDetailsView setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public MeetDetailsView setMeetType(String meetType) {
        this.meetType = meetType;
        return this;
    }

    public MeetDetailsView setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public MeetDetailsView setDescription(String description) {
        this.description = description;
        return this;
    }

    public MeetDetailsView setDate(Date date) {
        this.date = date;
        return this;
    }

    public MeetDetailsView setAnnouncer(String announcer) {
        this.announcer = announcer;
        return this;
    }

    public MeetDetailsView setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
        return this;
    }

    public MeetDetailsView setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }
}
