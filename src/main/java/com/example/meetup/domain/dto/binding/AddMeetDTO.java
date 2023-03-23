package com.example.meetup.domain.dto.binding;

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
public class AddMeetDTO {

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

    public AddMeetDTO setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public AddMeetDTO setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public AddMeetDTO setMeetType(String meetType) {
        this.meetType = meetType;
        return this;
    }

    public AddMeetDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public AddMeetDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public AddMeetDTO setDate(Date date) {
        this.date = date;
        return this;
    }
}
