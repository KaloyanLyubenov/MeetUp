package com.example.meetup.service;

import com.example.meetup.domain.dto.models.MeetTypeModel;
import com.example.meetup.domain.entities.MeetTypeEntity;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.repository.MeetTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetTypeService {

    public final MeetTypeRepository meetTypeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeetTypeService(MeetTypeRepository meetTypeRepository,
                           ModelMapper modelMapper)
    {
        this.meetTypeRepository = meetTypeRepository;
        this.modelMapper = modelMapper;
    }

    public MeetTypeModel findByType(MeetTypeEnum type){
        return this.modelMapper.map(
                this.meetTypeRepository.findByType(type).orElse(new MeetTypeEntity())
                , MeetTypeModel.class);
    }




}
