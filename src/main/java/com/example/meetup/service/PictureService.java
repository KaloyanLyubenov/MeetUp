package com.example.meetup.service;

import com.example.meetup.domain.dto.binding.AddPictureDTO;
import com.example.meetup.domain.entities.MeetEntity;
import com.example.meetup.domain.entities.PictureEntity;
import com.example.meetup.repository.MeetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PictureService {

    private final ImageCloudService imageCloudService;
    private final MeetRepository meetRepository;

    @Autowired
    public PictureService(ImageCloudService imageCloudService, MeetService meetService, MeetRepository meetRepository) {
        this.imageCloudService = imageCloudService;
        this.meetRepository = meetRepository;
    }

    public void addPicture(AddPictureDTO addPictureDTO){
        PictureEntity picture = new PictureEntity()
                .setUrl(imageCloudService.saveImage(addPictureDTO.getImage()));

        MeetEntity meet = this.meetRepository.findById(addPictureDTO.getIdOfMeet()).get();

        meet.getPictures().add(picture);

        this.meetRepository.saveAndFlush(meet);
    }


}
