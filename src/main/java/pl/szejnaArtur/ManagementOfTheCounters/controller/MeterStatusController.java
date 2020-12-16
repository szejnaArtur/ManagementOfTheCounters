package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.MeterStatus;

@Controller
@RequestMapping(value = "/meter_status")
public class MeterStatusController {

    @GetMapping("/add")
    public ModelAndView statusPanel(ModelAndView mav) {
        mav.setViewName("addStatus");
        mav.addObject("meter_status", new MeterStatus());
        return mav;
    }

}
