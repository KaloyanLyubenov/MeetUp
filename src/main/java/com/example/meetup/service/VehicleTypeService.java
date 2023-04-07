package com.example.meetup.service;

import com.example.meetup.domain.dto.models.VehicleTypeModel;
import com.example.meetup.domain.entities.VehicleTypeEntity;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import com.example.meetup.repository.VehicleTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeService {

    public final VehicleTypeRepository vehicleTypeRepository;
    public final ModelMapper modelMapper;

    @Autowired
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository,
                              ModelMapper modelMapper)
    {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.modelMapper = modelMapper;
    }

    public VehicleTypeModel findByType(VehicleTypeEnum type){
        return this.modelMapper.map(
                this.vehicleTypeRepository.findByType(type).orElse(new VehicleTypeEntity())
                , VehicleTypeModel.class);
    }
}
