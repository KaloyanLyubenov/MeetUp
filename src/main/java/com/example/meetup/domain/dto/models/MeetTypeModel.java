package com.example.meetup.domain.dto.models;

import com.example.meetup.domain.enums.MeetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetTypeModel {

    private Long id;
    private MeetTypeEnum meetType;

    public MeetTypeModel setId(Long id) {
        this.id = id;
        return this;
    }

    public MeetTypeModel setMeetType(MeetTypeEnum meetType) {
        this.meetType = meetType;
        return this;
    }
}
