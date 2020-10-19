package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.szejnaArtur.ManagementOfTheCounters.component.mailer.RandomStringFactory;
import pl.szejnaArtur.ManagementOfTheCounters.component.mailer.SignUpMailer;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {

    private static final String CANNOT_SIGN_UP_GIVEN_USER = "Can't sign up given user, it already has set id. User ";

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private SignUpMailer mailer;
    private static final int TOKEN_LENGTH = 20;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SignUpMailer mailer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailer = mailer;
    }

    @Override
    public User signUpUser(User user) {
        Assert.isNull(user.getUser_id(), CANNOT_SIGN_UP_GIVEN_USER + user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);
        user.setConfirmationToken(token);
        Thread thread = new Thread(() -> mailer.sendConfirmationLink(user.getEmail(), token));
        thread.start();
        return userRepository.save(user);
    }
}
