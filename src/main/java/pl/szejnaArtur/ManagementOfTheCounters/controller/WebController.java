package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.PropertyServiceImpl;

import java.util.List;

@Controller
public class WebController {

    private PropertyServiceImpl propertyService;
    private UserRepository userRepository;

    @Autowired
    public WebController(UserRepository userRepository, PropertyServiceImpl propertyService){
        this.userRepository = userRepository;
        this.propertyService = propertyService;
    }

    @RequestMapping(value="/user_panel", method=RequestMethod.GET)
    public ModelAndView userPanel(ModelAndView mav) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = propertyService.getPropertiesByUser(user);

        mav.addObject("properties", properties);

        mav.setViewName("user_panel");
        return mav;
    }

    @RequestMapping(value="/counters", method=RequestMethod.GET)
    public ModelAndView counterPanel(ModelAndView mav) {
        mav.setViewName("counter");
        return mav;
    }

    @RequestMapping(value="/analysis", method=RequestMethod.GET)
    public ModelAndView analysisPanel(ModelAndView mav) {
        mav.setViewName("analysis");
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
