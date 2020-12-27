package pl.szejnaArtur.ManagementOfTheCounters.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Property;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.PropertyServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/property")
public class PropertyController {

    private PropertyServiceImpl propertyService;
    private CounterServiceImpl counterService;

    @Autowired
    public PropertyController(PropertyServiceImpl propertyService, CounterServiceImpl counterService) {
        this.propertyService = propertyService;
        this.counterService = counterService;
    }

    @GetMapping
    public ModelAndView userPanel(ModelAndView mav) {
        List<Property> properties = propertyService.getPropertiesByUser();

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
    public String addPropertyPanel(@Valid @ModelAttribute Property property, Errors errors) {
        if (errors.hasErrors()) {
            return "addProperty";
        }

        propertyService.addProperty(property);
        return "redirect:/property";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewProperty(ModelAndView mav, @PathVariable("id") Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        if(property != null){
            mav.addObject("property", property);
            List<Counter> counters = counterService.getAllCountersByProperty(property);
            mav.setViewName("property");
            mav.addObject("counters", counters);
        } else {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateProperty(ModelAndView mav, @Valid @ModelAttribute Property property, Errors errors,
                                       @PathVariable("id") Long propertyId){
        if (errors.hasErrors()){
            Property oldProperty = propertyService.getProperty(propertyId);
            mav.addObject("counters", oldProperty.getCounters());
            mav.setViewName("property");
            return mav;
        }

        propertyService.updateProperty(property, propertyId);
        mav.setViewName("redirect:/property/view/"+propertyId);
        return mav;
    }
}
