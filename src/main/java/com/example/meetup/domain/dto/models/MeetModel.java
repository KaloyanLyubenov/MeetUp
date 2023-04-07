package com.example.meetup.domain.dto.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MeetModel {
    private Long id;
    private String meetTitle;
    private MeetTypeModel meetType;
    private VehicleTypeModel vehicleType;
    private String description;
    private Date date;
    private UserModel announcer;
    private List<PictureModel> pictures;
    private PictureModel thumbnail;
    private List<UserModel> participants;
    private List<CommentModel> comments;


    public MeetModel setId(Long id) {
        this.id = id;
        return this;
    }

    public MeetModel setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public MeetModel setMeetType(MeetTypeModel meetType) {
        this.meetType = meetType;
        return this;
    }

    public MeetModel setVehicleType(VehicleTypeModel vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public MeetModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MeetModel setDate(Date date) {
        this.date = date;
        return this;
    }

    public MeetModel setAnnouncer(UserModel announcer) {
        this.announcer = announcer;
        return this;
    }

    public MeetModel setPictures(List<PictureModel> pictures) {
        this.pictures = pictures;
        return this;
    }

    public MeetModel setThumbnail(PictureModel thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public MeetModel setParticipants(List<UserModel> participants) {
        this.participants = participants;
        return this;
    }

    public MeetModel setComments(List<CommentModel> comments) {
        this.comments = comments;
        return this;
    }
}
