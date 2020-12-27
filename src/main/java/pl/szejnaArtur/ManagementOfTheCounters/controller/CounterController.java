package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/counter")
public class CounterController {

    private CounterServiceImpl counterService;

    @Autowired
    public CounterController(CounterServiceImpl counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/add/{id}")
    public ModelAndView counterPanel(ModelAndView mav, @PathVariable("id") Long propertyId) {
        mav.setViewName("addCounter");
        mav.addObject("counter", new Counter());
        mav.addObject("propertyId", propertyId);
        return mav;
    }

    @PostMapping(value = "/add/{id}")
    public String addCounter(@Valid @ModelAttribute Counter counter, Errors errors, @PathVariable("id") Long propertyId) {
        if (errors.hasErrors()) {
            return "addCounter";
        }
        counterService.addCounter(counter, propertyId);
        return "redirect:/property";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewCounter(ModelAndView mav, @PathVariable("id") Long counterId) {
        Counter counter = counterService.getCounter(counterId);
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

        counterService.updateCounter(counter);
        mav.setViewName("redirect:/counter/view/" + counter.getCounterId());
        return mav;
    }

    @GetMapping("/view/all")
    public ModelAndView viewAllCounters(ModelAndView mav) {
        mav.addObject("counters", counterService.getAllCounters());
        mav.setViewName("counters");
        return mav;
    }
}
