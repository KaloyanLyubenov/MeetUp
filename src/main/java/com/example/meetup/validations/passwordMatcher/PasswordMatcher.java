package com.example.meetup.validations.passwordMatcher;

import com.example.meetup.domain.dto.binding.UserRegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatcher implements ConstraintValidator<PasswordMatch, UserRegisterDTO> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegisterDTO userRegisterDTO, ConstraintValidatorContext context) {
        if (userRegisterDTO.getPassword() != null &&
                userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(userRegisterDTO.getConfirmPassword())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
