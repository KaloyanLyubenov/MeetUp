package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meets")
public class MeetEntity extends BaseEntity{

    @Column(name = "meet_title", nullable = false)
    private String meetTitle;

    @Enumerated(EnumType.STRING)
    private MeetTypeEnum meetType;

    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum vehicleType;

    @Column
    private String description;

    @Column
    private Date date;

    @ManyToOne
    private UserEntity announcer;

}
