package pl.szejnaArtur.ManagementOfTheCounters.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.component.mailer.SignUpMailer;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;

import java.util.Optional;

@RestController
public class ResetPasswordRestController {

    private final static String SUBJECT = "Link to reset your password";
    private final static String TEXT = "You received this email because you used the password recovery option.\n\n"
            + "If you did not ask for it, it is possible that someone did it by mistake or on purpose.\n"
            + "If you don't want to change your hamster password, you can just ignore this message.\n\n"
            + "To change your password, click on the link below:\n";
    private final static String LINK = "http://localhost:8080/reset_password/";

    private final static String NULL_POINTER_EXCEPTION_MESSAGE = "The user with the given e-mail address does not exist";

    private SignUpMailer mailer;
    private UserRepository userRepository;

    @Autowired
    public ResetPasswordRestController(SignUpMailer mailer, UserRepository userRepository) {
        this.mailer = mailer;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/send_reset_mail", method = RequestMethod.GET)
    public String sendResetPassword(@RequestParam("email") String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
        }
        User user = optionalUser.get();

        Thread thread = new Thread(() -> mailer.sandMessage(email, SUBJECT, TEXT + LINK + user.getConfirmationToken()));
        thread.start();

        return "An email with a link to reset your password has been sent.";
    }
}
