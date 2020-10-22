package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private CounterServiceImpl counterService;

    @Autowired
    public WebController(CounterServiceImpl counterService){
        this.counterService = counterService;
    }

    @RequestMapping(value="/user_panel", method=RequestMethod.GET)
    public ModelAndView userPanel(ModelAndView mav) {
        List<Counter> counters = new ArrayList<>(counterService.getAllCounters());
        mav.addObject("counters", counters);

        mav.setViewName("user_panel");
        return mav;
    }

    @RequestMapping(value="/sign_up", method=RequestMethod.GET)
    public ModelAndView signUpPanel(ModelAndView mav) {
        mav.setViewName("sign_up");
        return mav;
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value="/forgot_password", method=RequestMethod.GET)
    public ModelAndView forgotPassword(ModelAndView mav) {
        mav.setViewName("forgot_password");
        return mav;
    }

    @RequestMapping(value="/reset_password", method=RequestMethod.GET)
    public ModelAndView resetPassword(ModelAndView mav) {
        mav.setViewName("reset_password");
        return mav;
    }
}
