package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByToken(String token) throws UsernameNotFoundException {
        Optional<User> emailOptional = userRepository.findByConfirmationToken(token);

        if (!emailOptional.isPresent()) {
            throw new UsernameNotFoundException("No user found with the given token");
        }

        return emailOptional.get();
    }
}
