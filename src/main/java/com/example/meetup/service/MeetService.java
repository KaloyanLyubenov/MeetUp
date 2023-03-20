package com.example.meetup.service;

import com.example.meetup.domain.dto.MeetModel;
import com.example.meetup.domain.dto.MeetTypeModel;
import com.example.meetup.domain.dto.UserModel;
import com.example.meetup.domain.dto.VehicleTypeModel;
import com.example.meetup.domain.dto.binding.AddMeetModel;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.entities.MeetEntity;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import com.example.meetup.repository.MeetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetService {

    private final UserService userService;
    private final MeetTypeService meetTypeService;
    private final MeetRepository meetRepository;
    private final VehicleTypeService vehicleTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeetService(UserService userService,
                       MeetTypeService meetTypeService,
                       MeetRepository meetRepository,
                       VehicleTypeService vehicleTypeService,
                       ModelMapper modelMapper)
    {
        this.userService = userService;
        this.meetTypeService = meetTypeService;
        this.meetRepository = meetRepository;
        this.vehicleTypeService = vehicleTypeService;
        this.modelMapper = modelMapper;
    }

    public void addMeet(AddMeetModel addMeetModel){
        UserModel user = this.userService.getUserByUsername(getLoggedUserUsername());
        MeetTypeModel meetType = this.meetTypeService.findByType(MeetTypeEnum.valueOf(addMeetModel.getMeetType()));
        VehicleTypeModel vehicleType = this.vehicleTypeService.findByType(VehicleTypeEnum.valueOf(addMeetModel.getVehicleType()));

        MeetEntity meetToSave = this.modelMapper.map(
                new MeetModel()
                        .setMeetTitle(addMeetModel.getMeetTitle())
                        .setMeetType(meetType)
                        .setVehicleType(vehicleType)
                        .setDescription(addMeetModel.getDescription())
                        .setDate(addMeetModel.getDate())
                        .setAnnouncer(user)
                , MeetEntity.class);

        this.meetRepository.saveAndFlush(meetToSave);

    }

    private String getLoggedUserUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = (((UserDetails) principal).getUsername());
        }else{
            username = principal.toString();
        }

        return username;
    }

    public List<MeetIndexView> getAllMeets() {
        return meetRepository.findAll()
                .stream().map(meet -> new MeetIndexView()
                        .setId(meet.getId())
                        .setMeetTitle(meet.getMeetTitle())
                        .setDescription(meet.getDescription())
                ).collect(Collectors.toList());
    }

}
