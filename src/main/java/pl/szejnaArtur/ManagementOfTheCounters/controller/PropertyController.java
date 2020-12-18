package pl.szejnaArtur.ManagementOfTheCounters.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.entity.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.PropertyServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/property")
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

    @GetMapping
    public ModelAndView userPanel(ModelAndView mav) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = propertyService.getPropertiesByUser(user);

        mav.addObject("properties", properties);

        mav.setViewName("properties");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addPropertyPanel(ModelAndView mav) {
        mav.addObject("property", new Property());
        mav.setViewName("addProperty");
        return mav;
    }

    @PostMapping("/add")
    public String addPropertyPanel2(@Valid @ModelAttribute Property property, Errors errors) {
        if (errors.hasErrors()) {
            return "addProperty";
        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        property.setUser(user);
        propertyService.addProperty(property);
        log.info("Dodano obiekt do bazy: " + property);
        return "redirect:/property";
    }

    @GetMapping("/view/{id}")
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
