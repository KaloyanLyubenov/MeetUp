package com.example.meetup.domain.dto.binding;

import com.example.meetup.domain.dto.PictureModel;
import com.example.meetup.domain.entities.BaseEntity;
import com.example.meetup.validations.passwordMatcher.PasswordMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatch
public class UserRegisterDTO extends BaseEntity {


    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private MultipartFile profilePicture;


}
