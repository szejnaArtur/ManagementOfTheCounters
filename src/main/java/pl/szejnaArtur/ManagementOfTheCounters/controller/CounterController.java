package pl.szejnaArtur.ManagementOfTheCounters.controller;

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
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/counter")
public class CounterController {

    private CounterServiceImpl counterService;
    private PropertyService propertyService;
    private UserRepository userRepository;

    @Autowired
    public CounterController(PropertyService propertyService, CounterServiceImpl counterService, UserRepository userRepository) {
        this.counterService = counterService;
        this.propertyService = propertyService;
        this.userRepository = userRepository;
    }

    @GetMapping("/add")
    public ModelAndView counterPanel(ModelAndView mav, @RequestParam("property-id") String propertyId) {
        mav.setViewName("addCounter");
        mav.addObject("counter", new Counter());
        mav.addObject("propertyId", propertyId);
        return mav;
    }

    @PostMapping(value = "/add")
    public String addCounter(@Valid @ModelAttribute Counter counter, Errors errors, @RequestParam("property-id") String propertyId) {
        if (errors.hasErrors()) {
            return "addCounter";
        }

        Long propertyLong = Long.valueOf(propertyId);
        Property property = propertyService.getProperty(propertyLong);
        counter.setProperty(property);
        counterService.addCounter(counter);
        return "redirect:/property";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewCounter(ModelAndView mav, @PathVariable("id") Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = user.getProperties();
        for (Property property : properties) {
            List<Counter> counters = property.getCounters();
            for (Counter counter : counters) {
                if (counter.getCounterId().equals(id)) {
                    mav.addObject("counter", counterService.getCounter(id));
                    mav.setViewName("counter");
                    break;
                }
            }
        }
        return mav;
    }

    @GetMapping("/view/all")
    public ModelAndView viewAllCounters(ModelAndView mav) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = user.getProperties();
        List<Counter> counters = new ArrayList<>();
        for (Property property : properties) {
            counters.addAll(property.getCounters());
        }
        mav.addObject("counters", counters);
        mav.setViewName("counters");
        return mav;
    }
}
