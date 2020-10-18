package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.service.SignUpService;

@Controller
public class SignUpRestController {

    private SignUpService signUpService;

    @Autowired
    public SignUpRestController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping(value = "/sign_up")
    public ModelAndView signUp(ModelAndView modelAndView, @RequestParam("email") String email, @RequestParam("password") String password){
        modelAndView.setViewName("redirect:/login");
        signUpService.signUpUser(new User(email, password));
        return modelAndView;
    }
}
