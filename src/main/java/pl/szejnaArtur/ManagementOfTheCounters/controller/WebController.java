package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");

        return mav;
    }
}
