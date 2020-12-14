package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.component.mailer.RandomStringFactory;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.UserServiceImpl;

@Controller
public class ResetPasswordController {

    private static final int TOKEN_LENGTH = 15;

    private UserServiceImpl userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ResetPasswordController(UserServiceImpl userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public ModelAndView forgotPassword(ModelAndView mav) {
        mav.setViewName("forgot_password");
        return mav;
    }

    @RequestMapping(value = "/reset_password/{token}", method = RequestMethod.GET)
    public ModelAndView resetPassword(ModelAndView mav, @PathVariable(name = "token") String token) {
        User user = userService.loadUserByToken(token);
        mav.addObject("user", user);
        mav.setViewName("reset_password");
        return mav;
    }

    @RequestMapping("reset_credentials")
    @ResponseBody
    public ModelAndView resetCredentials(@RequestParam(name = "confirmationToken") String token,
                                         @RequestParam(name = "password") String password, ModelAndView mav) {
        User user = userService.loadUserByToken(token);
        user.setPassword(passwordEncoder.encode(password));
        String newToken = RandomStringFactory.getRandomString(TOKEN_LENGTH);
        user.setConfirmationToken(newToken);
        userRepository.save(user);
        mav.setViewName("redirect:/login");
        return mav;
    }
}
