package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meets")
public class Meet extends BaseEntity{

    @Column(name = "meet_title", nullable = false)
    private String meetTitle;

    @Enumerated(EnumType.STRING)
    private MeetTypeEnum meetType;

    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum vehicleType;

    @ManyToOne
    private UserEntity announcer;

}
