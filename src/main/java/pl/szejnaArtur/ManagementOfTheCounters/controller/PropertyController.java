package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.PropertyServiceImpl;

@Controller
public class PropertyController {

    private PropertyServiceImpl propertyService;
    private UserRepository userRepository;

    @Autowired
    public PropertyController(PropertyServiceImpl propertyService, UserRepository userRepository) {
        this.propertyService = propertyService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/property/add", method = RequestMethod.GET)
    public ModelAndView addPropertyPanel(ModelAndView mav, @RequestParam("name") String name,
                                         @RequestParam("street") String street, @RequestParam("houseNumber") String houseNumber,
                                         @RequestParam("flatNumber") String flatNumber, @RequestParam("postalCode") String postalCode,
                                         @RequestParam("city") String city) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        Property property = Property.of(name, street, houseNumber, flatNumber, postalCode, city, user);
        propertyService.addProperty(property);
        mav.setViewName("redirect:/user_panel");
        return mav;
    }

}
