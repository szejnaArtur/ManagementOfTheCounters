package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/counter")
public class CounterController {

    private CounterServiceImpl counterService;
    private PropertyService propertyService;

    @Autowired
    public CounterController(PropertyService propertyService, CounterServiceImpl counterService) {
        this.counterService = counterService;
        this.propertyService = propertyService;
    }

    @GetMapping("/add")
    public ModelAndView counterPanel(ModelAndView mav, @RequestParam("property-id") String propertyId) {
        mav.setViewName("addCounter");
        mav.addObject("counter", new Counter());
        mav.addObject("propertyId", propertyId);
        return mav;
    }

    @PostMapping(value = "/add")
    public String addPropertyPanel(@Valid @ModelAttribute Counter counter, Errors errors) {
        if (errors.hasErrors()) {
            return "addCounter";
        }

//        Property property = propertyService.getProperty(propertyID);
//        counter.setProperty(property);
        counterService.addCounter(counter);
        return "redirect:/user_panel";
    }
}
