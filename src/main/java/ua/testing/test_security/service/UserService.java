package ua.testing.test_security.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.testing.test_security.dto.UserDTO;
import ua.testing.test_security.dto.UsersDTO;
import ua.testing.test_security.entity.RoleType;
import ua.testing.test_security.entity.User;
import ua.testing.test_security.repository.UserRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /*@PostConstruct
    public void init(){
        if (!userRepository.findUserByUsername("admin").isPresent()){
            userRepository.save(User.builder()
                    .firstName("ghj")
                    .lastName("hjk")
                    .email("qwfg@dfgfghj")
                    .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .role(RoleType.ROLE_ADMIN)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true).build());
        }
    }*/

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found!"));
    }


    public UsersDTO getAllUsers() {
        //TODO checking for an empty user list
        return new UsersDTO(userRepository.findAll());
    }

}
