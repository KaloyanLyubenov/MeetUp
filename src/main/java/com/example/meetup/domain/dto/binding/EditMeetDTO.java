package com.example.meetup.domain.dto.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EditMeetDTO {

    private Long id;
    private String meetTitle;
    private String meetType;
    private String vehicleType;
    private String description;
    private String authorUsername;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    public EditMeetDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public EditMeetDTO setMeetTitle(String meetTitle) {
        this.meetTitle = meetTitle;
        return this;
    }

    public EditMeetDTO setMeetType(String meetType) {
        this.meetType = meetType;
        return this;
    }

    public EditMeetDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public EditMeetDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public EditMeetDTO setDate(Date date) {
        this.date = date;
        return this;
    }

    public EditMeetDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }
}
