package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;
import pl.szejnaArtur.ManagementOfTheCounters.service.SignUpService;

@Controller
public class SignUpController {

    private static final String SUCCESS_SIGN_UP = "The user was created. Confirm your registration using the email link.";

    private SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping(value = "/sign_up")
    public ModelAndView signUp(ModelAndView modelAndView, @RequestParam("email") String email, @RequestParam("password") String password) {
        modelAndView.setViewName("redirect:/login");
        modelAndView.addObject("success_message", SUCCESS_SIGN_UP);
        signUpService.signUpUser(User.of(email, password));
        return modelAndView;
    }
}
