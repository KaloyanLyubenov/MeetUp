package com.example.meetup.domain.dto.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPictureDTO {

    private MultipartFile image;

    private Long idOfMeet;

    public AddPictureDTO setIdOfMeet(Long idOfMeet) {
        this.idOfMeet = idOfMeet;
        return this;
    }

    public AddPictureDTO setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
