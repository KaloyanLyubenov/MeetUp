package com.example.meetup.service;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.binding.AddCommentDTO;
import com.example.meetup.domain.dto.binding.AddMeetDTO;
import com.example.meetup.domain.dto.binding.EditMeetDTO;
import com.example.meetup.domain.dto.models.MeetModel;
import com.example.meetup.domain.dto.models.MeetTypeModel;
import com.example.meetup.domain.dto.models.UserModel;
import com.example.meetup.domain.dto.models.VehicleTypeModel;
import com.example.meetup.domain.dto.views.MeetDetailsView;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.dto.views.PopularMeetView;
import com.example.meetup.domain.entities.*;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import com.example.meetup.repository.CommentRepository;
import com.example.meetup.repository.MeetRepository;
import com.example.meetup.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//ErrorHandling Implemented

@Service
public class MeetService {

    private final UserService userService;
    private final MeetTypeService meetTypeService;
    private final MeetRepository meetRepository;
    private final VehicleTypeService vehicleTypeService;
    private final ModelMapper modelMapper;
    private final ImageCloudService imageCloudService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public MeetService(UserService userService,
                       MeetTypeService meetTypeService,
                       MeetRepository meetRepository,
                       VehicleTypeService vehicleTypeService,
                       ModelMapper modelMapper,
                       ImageCloudService imageCloudService,
                       UserRepository userRepository,
                       CommentRepository commentRepository)
    {
        this.userService = userService;
        this.meetTypeService = meetTypeService;
        this.meetRepository = meetRepository;
        this.vehicleTypeService = vehicleTypeService;
        this.modelMapper = modelMapper;
        this.imageCloudService = imageCloudService;

        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public void addMeet(AddMeetDTO addMeetDTO){
        UserModel user = this.userService.getUserByUsername(getLoggedUserUsername());
        MeetTypeModel meetType = this.meetTypeService.findByType(MeetTypeEnum.valueOf(addMeetDTO.getMeetType()));
        VehicleTypeModel vehicleType = this.vehicleTypeService.findByType(VehicleTypeEnum.valueOf(addMeetDTO.getVehicleType()));

        MeetEntity meetToSave = this.modelMapper.map(
                new MeetModel()
                        .setMeetTitle(addMeetDTO.getMeetTitle())
                        .setMeetType(meetType)
                        .setVehicleType(vehicleType)
                        .setDescription(addMeetDTO.getDescription())
                        .setDate(addMeetDTO.getDate())
                        .setAnnouncer(user)
                        .setPictures(new ArrayList<>())
                , MeetEntity.class);

        PictureEntity picture = new PictureEntity()
                .setUrl(imageCloudService.saveImage(addMeetDTO.getImage()));
        meetToSave.addPicture(picture);

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
                        .setDate(meet.getDate())
                        .setThumbnailUrl(meet.getThumbnail().getUrl())
                ).collect(Collectors.toList());
    }

    public MeetDetailsView getMeetDetails(Long id) {
        MeetEntity meet = this.modelMapper.map(this.meetRepository.findById(id), MeetEntity.class);

        if(meet == null){
            throw new ObjectNotFoundException(id.toString(), "ID", "Meet");
        }

        List<String> pictureUrls = meet.getPictures()
                .stream().map(PictureEntity::getUrl).toList();
        List<Long> participantIds = meet.getParticipants()
                .stream().map(BaseEntity::getId).toList();

        UserModel announcer = this.userService.getUserById(meet.getAnnouncer().getId());

        return new MeetDetailsView()
                .setId(meet.getId())
                .setMeetTitle(meet.getMeetTitle())
                .setMeetType(meet.getMeetType().toString())
                .setVehicleType(meet.getVehicleType().toString())
                .setDescription(meet.getDescription())
                .setDate(meet.getDate())
                .setAnnouncer(announcer.getFirstName() + " " + announcer.getLastName())
                .setPictureUrls(pictureUrls)
                .setThumbnailUrl(meet.getThumbnail().getUrl())
                .setParticipantIds(participantIds);
    }

    public List<PopularMeetView> getPopularMeets(){
        return meetRepository.findFirst4ByOrderByIdAsc()
                .stream().map(meet -> new PopularMeetView()
                        .setId(meet.getId())
                        .setMeetType(meet.getMeetType().toString())
                        .setMeetTitle(meet.getMeetTitle())
                        .setThumbnailUrl(meet.getThumbnail().getUrl()))
                .collect(Collectors.toList());
    }

    public EditMeetDTO findMeetToEditById(Long id){
        MeetModel meet = this.modelMapper.map(this.meetRepository.findById(id), MeetModel.class);

        if(meet == null){
            throw new ObjectNotFoundException(id.toString(), "ID", "Meet");
        }

        return new EditMeetDTO()
                .setId(meet.getId())
                .setMeetTitle(meet.getMeetTitle())
                .setMeetType(meet.getMeetType().toString())
                .setVehicleType(meet.getVehicleType().toString())
                .setDate(meet.getDate())
                .setDescription(meet.getDescription());
    }

    public void editMeet(EditMeetDTO editMeetDTO){
        MeetModel meetToEdit = this.modelMapper.map(this.meetRepository.findById(editMeetDTO.getId()), MeetModel.class);

        meetToEdit
                .setMeetTitle(editMeetDTO.getMeetTitle())
                .setMeetType(this.meetTypeService.findByType(MeetTypeEnum.valueOf(editMeetDTO.getMeetType())))
                .setVehicleType(this.vehicleTypeService.findByType(VehicleTypeEnum.valueOf(editMeetDTO.getVehicleType())))
                .setDescription(editMeetDTO.getDescription());

        this.meetRepository.saveAndFlush(
                this.modelMapper.map(
                        meetToEdit, MeetEntity.class
                )
        );
    }

    public List<MeetIndexView> getMeetsIndexViewByAnnouncerId(Long announcerId){
        List<MeetEntity> meets = this.meetRepository.findAllByAnnouncer_Id(announcerId);

        if(meets == null){
            throw new ObjectNotFoundException(announcerId.toString(), "AnnouncerID", "List of Meets");
        }

        return meets
                .stream().map(meet -> new MeetIndexView()
                        .setId(meet.getId())
                        .setMeetTitle(meet.getMeetTitle())
                        .setDate(meet.getDate())
                        .setDescription(meet.getDescription())
                        .setThumbnailUrl(meet.getThumbnail().getUrl()))
                .collect(Collectors.toList());
    }

    public void addParticipant(Long meetId, Long userId){
        MeetEntity meet = this.modelMapper.map(this.meetRepository.findById(meetId), MeetEntity.class);

        if(meet == null){
            throw new ObjectNotFoundException(meetId.toString(), "ID", "Meet");
        }

        UserEntity user = this.modelMapper.map(this.userRepository.findById(userId), UserEntity.class);

        this.meetRepository.saveAndFlush(meet.addParticipant(user));
    }

    public void removeParticipant(Long meetId, Long userId){
        MeetEntity meet = this.modelMapper.map(this.meetRepository.findById(meetId), MeetEntity.class);

        if(meet == null){
            throw new ObjectNotFoundException(meetId.toString(), "ID", "Meet");
        }

        UserEntity user = this.modelMapper.map(this.userRepository.findById(userId), UserEntity.class);

        meet = meet.removeParticipant(user);

        this.meetRepository.saveAndFlush(meet);
    }

    public void addComment(AddCommentDTO addCommentDTO){
        MeetEntity meetToSave = this.modelMapper.map(this.meetRepository.findById(addCommentDTO.getMeetId()), MeetEntity.class);

        if(meetToSave == null){
            throw new ObjectNotFoundException(addCommentDTO.getMeetId().toString(), "ID", "Meet");
        }

        CommentEntity commentToAdd = this.modelMapper.map(this.commentRepository.findById(addCommentDTO.getId()), CommentEntity.class);

        List<CommentEntity> comments = meetToSave.getComments();
        comments.add(commentToAdd);
        meetToSave.setComments(comments);

        this.meetRepository.save(meetToSave);
    }



}
