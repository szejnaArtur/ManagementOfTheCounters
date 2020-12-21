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
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.MeterStatusServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/meter_status")
public class MeterStatusController {

    private UserRepository userRepository;
    private MeterStatusServiceImpl meterStatusService;

    @Autowired
    public MeterStatusController(UserRepository userRepository, MeterStatusServiceImpl meterStatusService) {
        this.userRepository = userRepository;
        this.meterStatusService = meterStatusService;
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
                            @RequestParam("counterId") String counterId) {
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
            if (counter.getCounterId().equals(counterLong)) {
                meter_status.setCounter(counter);
                meterStatusService.saveOrUpdate(meter_status);
                break;
            }
        }

        return "redirect:/counter/view/" + counterId;
    }

    @GetMapping("/delete/{meterStatusId}")
    public ModelAndView deleteMeterStatus(@PathVariable("meterStatusId") Long meterStatusId, ModelAndView mav) {
        Optional<MeterStatus> optionalId = meterStatusService.findById(meterStatusId);
        if (optionalId.isPresent()) {
            MeterStatus meterStatus = optionalId.get();
            Long counterId = meterStatus.getCounter().getCounterId();
            mav.setViewName("redirect:/counter/view/" + counterId);
            meterStatusService.delete(meterStatus);
        } else {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/update/{meterStatusId}")
    public ModelAndView updateMeterCounter(ModelAndView mav, @PathVariable("meterStatusId") Long meterStatusId, @RequestParam("meterStatus") Double status){
        Optional<MeterStatus> optionalMeterStatus = meterStatusService.findById(meterStatusId);
        if(optionalMeterStatus.isPresent()){
            MeterStatus meterStatus = optionalMeterStatus.get();
            meterStatus.setStatus(status);
            meterStatusService.saveOrUpdate(meterStatus);
            mav.setViewName("redirect:/counter/view/" + meterStatus.getCounter().getCounterId());
        } else {
            mav.setViewName("error");
        }

        return mav;
    }
}
