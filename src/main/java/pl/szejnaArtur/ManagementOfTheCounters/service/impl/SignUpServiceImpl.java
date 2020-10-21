package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.szejnaArtur.ManagementOfTheCounters.component.mailer.RandomStringFactory;
import pl.szejnaArtur.ManagementOfTheCounters.component.mailer.SignUpMailer;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Role;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.RoleRepository;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.SignUpService;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    private static final String CANNOT_SIGN_UP_GIVEN_USER = "Can't sign up given user, it already has set id. User ";

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private SignUpMailer mailer;
    private RoleRepository roleRepository;

    private static final int TOKEN_LENGTH = 20;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SignUpMailer mailer, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailer = mailer;
        this.roleRepository = roleRepository;
    }

    @Override
    public User signUpUser(User user) {
        Assert.isNull(user.getUser_id(), CANNOT_SIGN_UP_GIVEN_USER + user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);
        user.setConfirmationToken(token);
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        roleOptional.ifPresent(role -> user.getRoles().add(role));
        Thread thread = new Thread(() -> mailer.sendConfirmationLink(user.getEmail(), token));
        thread.start();
        return userRepository.save(user);
    }
}
