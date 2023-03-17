package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.VehicleTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_types")
public class VehicleTypeEntity extends BaseEntity{

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum type;

    public VehicleTypeEntity setType(VehicleTypeEnum type) {
        this.type = type;
        return this;
    }
}
