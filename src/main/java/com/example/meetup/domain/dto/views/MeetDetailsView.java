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

    private Long id;
    private String meetTitle;
    private String meetType;
    private String vehicleType;
    private String description;
    private Date date;
    private String announcer;
    private List<String> pictureUrls = new ArrayList<>();
    private String thumbnailUrl;
    private List<Long> participantIds;
    private Boolean viewerParticipates;

    public MeetDetailsView setId(Long id) {
        this.id = id;
        return this;
    }

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

    public MeetDetailsView setViewerParticipates(Long viewerId) {
        if(this.participantIds.contains(viewerId)){
            this.viewerParticipates = true;
        }else{
            this.viewerParticipates = false;
        }

        return this;
    }

    public MeetDetailsView setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
        return this;
    }

    public MeetDetailsView setViewerParticipates(Boolean viewerParticipates) {
        this.viewerParticipates = viewerParticipates;
        return this;
    }
}
