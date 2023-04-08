package com.example.meetup.domain.dto.models;

import com.example.meetup.domain.enums.VehicleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeModel {

    private Long id;
    private VehicleTypeEnum vehicleType;

    public VehicleTypeModel setId(Long id) {
        this.id = id;
        return this;
    }

    public VehicleTypeModel setVehicleType(VehicleTypeEnum vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    @Override
    public String toString(){
        return this.vehicleType.name();
    }
}
