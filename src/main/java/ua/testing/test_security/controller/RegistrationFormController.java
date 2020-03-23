package ua.testing.test_security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.testing.test_security.entity.RoleType;
import ua.testing.test_security.entity.User;
import ua.testing.test_security.service.RegistrationFormService;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class RegistrationFormController {
    RegistrationFormService registrationFormService;
    @Autowired
    public RegistrationFormController(RegistrationFormService registrationFormService) {
        this.registrationFormService = registrationFormService;
    }
    @ResponseStatus( HttpStatus.CREATED)
    @PostMapping(value = "reg")
    public void regFormController(User user) {
        registrationFormService.saveNewUser(User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .role(RoleType.ROLE_USER)
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .build());
        log.info("{}", user);
    }
}
