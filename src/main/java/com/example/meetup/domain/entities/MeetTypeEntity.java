package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.MeetTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meet_types")
public class MeetTypeEntity extends BaseEntity{

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private MeetTypeEnum type;

    public MeetTypeEntity setMeetType(MeetTypeEnum type) {
        this.type = type;
        return this;
    }
}
