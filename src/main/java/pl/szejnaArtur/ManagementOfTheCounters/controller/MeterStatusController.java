package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.MeterStatus;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.entity.repository.MeterStatusRepository;
import pl.szejnaArtur.ManagementOfTheCounters.entity.repository.UserRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/meter_status")
public class MeterStatusController {

    private UserRepository userRepository;
    private MeterStatusRepository meterStatusRepository;

    @Autowired
    public MeterStatusController(UserRepository userRepository, MeterStatusRepository meterStatusRepository) {
        this.userRepository = userRepository;
        this.meterStatusRepository = meterStatusRepository;
    }

    @GetMapping("/add")
    public ModelAndView statusPanel(ModelAndView mav, @RequestParam("counter-id") String counterId) {
        mav.setViewName("addStatus");
        mav.addObject("meterStatus", new MeterStatus());
        mav.addObject("counterId", counterId);
        return mav;
    }

    @PostMapping("/add")
    public String addStatus(@Valid @ModelAttribute MeterStatus meter_status, Errors errors,
                            @RequestParam("counter-id") String counterId) {
        if (errors.hasErrors()) {
            return "addStatus";
        }

        Long counterLong = Long.valueOf(counterId);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = user.getProperties();
        List<Counter> counters = new ArrayList<>();
        for (Property property : properties) {
            counters.addAll(property.getCounters());
        }

        for (Counter counter : counters) {
            if (counter.getCounterId().equals(counterLong)){
                meter_status.setCounter(counter);
                meterStatusRepository.save(meter_status);
                break;
            }
        }

        return "redirect:/counter/view/" + counterId;
    }

}
