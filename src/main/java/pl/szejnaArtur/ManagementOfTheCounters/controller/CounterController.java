package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.MeterStatus;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Property;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

import javax.validation.Valid;
import java.util.*;

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
        counterService.addOrUpdateCounter(counter);
        return "redirect:/property";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewCounter(ModelAndView mav, @PathVariable("id") Long id) {
        Counter counter = counterService.getCounter(id);
        if (counter != null){
            mav.addObject("counter", counter);
            mav.setViewName("counter");
        } else {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/update")
    public ModelAndView updateCounter(ModelAndView mav, @Valid @ModelAttribute Counter counter, Errors errors) {
        if (errors.hasErrors()) {
            mav.setViewName("counter");
            return mav;
        }

        Counter newCounter = counterService.getCounter(counter.getCounterId());
        newCounter.updateCounter(counter);

        counterService.addOrUpdateCounter(newCounter);
        mav.setViewName("redirect:/counter/view/" + counter.getCounterId());
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
