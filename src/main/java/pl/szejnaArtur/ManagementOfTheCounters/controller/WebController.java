package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @RequestMapping(value="/user_panel", method=RequestMethod.GET)
    public ModelAndView userPanel(ModelAndView mav) {
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
}
