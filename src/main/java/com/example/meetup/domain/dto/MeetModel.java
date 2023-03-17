package com.example.meetup.domain.dto;


import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Date;

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
}
