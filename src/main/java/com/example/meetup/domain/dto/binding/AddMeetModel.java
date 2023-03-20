package com.example.meetup.domain.dto.binding;

import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMeetModel {

    @Size(min = 2, max = 20)
    @NotNull
    private String meetTitle;

    @NotNull
    private String meetType;

    @NotNull
    private String vehicleType;

    @Size(min = 5)
    @NotNull
    private String description;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    private MultipartFile image;

    public AddMeetModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public AddMeetModel setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public AddMeetModel setMeetType(String meetType) {
        this.meetType = meetType;
        return this;
    }

    public AddMeetModel setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public AddMeetModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public AddMeetModel setDate(Date date) {
        this.date = date;
        return this;
    }
}
