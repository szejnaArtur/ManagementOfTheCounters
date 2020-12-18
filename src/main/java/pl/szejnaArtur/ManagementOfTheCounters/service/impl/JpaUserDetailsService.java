package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.entity.repository.UserRepository;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> emailOptional = userRepository.findByEmail(email);

        if (!emailOptional.isPresent()) {
            throw new UsernameNotFoundException("No user found with e-mail: " + email);
        }

        return emailOptional.get();
    }
}
