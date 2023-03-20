package com.example.meetup.domain.entities;

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
@Entity
@Table(name = "meets")
public class MeetEntity extends BaseEntity{

    @Column(name = "meet_title", nullable = false)
    private String meetTitle;

    @ManyToOne
    private MeetTypeEntity meetType;

    @ManyToOne
    private VehicleTypeEntity vehicleType;

    @Column
    private String description;

    @Column
    private Date date;

    @ManyToOne
    private UserEntity announcer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PictureEntity> pictures = new ArrayList<>();

    public MeetEntity setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public MeetEntity setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public MeetEntity setMeetType(MeetTypeEntity meetType) {
        this.meetType = meetType;
        return this;
    }

    public MeetEntity setVehicleType(VehicleTypeEntity vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public MeetEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public MeetEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public MeetEntity setAnnouncer(UserEntity announcer) {
        this.announcer = announcer;
        return this;
    }

    public void addPicture(PictureEntity picture){
        this.pictures.add(picture);
    }
}
