package pl.szejnaArtur.ManagementOfTheCounters.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;

import java.util.Optional;

@RestController
public class SignUpRestController {

    private UserRepository userRepository;

    @Autowired
    public SignUpRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping("/confirm_email")
    public String confirmEmail(@RequestParam(name = "token") String token){
        Optional<User> optionalUser = userRepository.findByConfirmationToken(token);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
            return "Your account has been activated";
        } else {
            return "Given token does not exist.";
        }
    }

}
