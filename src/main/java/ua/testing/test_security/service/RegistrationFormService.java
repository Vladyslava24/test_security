package ua.testing.test_security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.testing.test_security.entity.User;
import ua.testing.test_security.repository.UserRepository;

@Slf4j
@Service
public class RegistrationFormService {
    private final UserRepository userRepository;
    @Autowired
    public RegistrationFormService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser (User user){
        //TODO inform the user about the replay email
        // TODO exception to endpoint
        try {
            userRepository.save(user);
        } catch (Exception ex){
            log.info("{Почтовый адрес уже существует}");
        }

    }
}
