package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.PropertyServiceImpl;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class PropertyController {

    private PropertyServiceImpl propertyService;
    private CounterServiceImpl counterService;
    private UserRepository userRepository;

    @Autowired
    public PropertyController(PropertyServiceImpl propertyService, UserRepository userRepository, CounterServiceImpl counterService) {
        this.propertyService = propertyService;
        this.userRepository = userRepository;
        this.counterService = counterService;
    }

    @RequestMapping(value = "/property/addPropertyPanel", method = RequestMethod.GET)
    public ModelAndView addPropertyPanel(ModelAndView mav) {
        mav.setViewName("addProperty");
        return mav;
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

    @RequestMapping(value = "/property/view/{id}")
    public ModelAndView viewProperty(ModelAndView mav, @PathVariable("id") Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = propertyService.getPropertiesByUser(user);
        Optional<Property> optionalProperty = properties.stream().filter(i -> i.getPropertyId().equals(id)).findFirst();
        if (optionalProperty.isPresent()) {
            Property property = optionalProperty.get();
            mav.addObject("property", property);
            List<Counter> counters = counterService.getAllCountersByProperty(property);
            mav.setViewName("property");
            mav.addObject("counters", counters);

        } else {
            mav.setViewName("error");
        }
        return mav;
    }

}
