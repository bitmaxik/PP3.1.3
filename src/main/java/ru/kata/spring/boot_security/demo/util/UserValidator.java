package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserValidatorService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserValidatorService userValidatorService;

    @Autowired
    public UserValidator(UserValidatorService userValidatorService) {
        this.userValidatorService = userValidatorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Optional<User> user = userValidatorService.checkUserByUsername(((User) target).getUsername());
        if (user.isEmpty())
            return;

        errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
    }
}
