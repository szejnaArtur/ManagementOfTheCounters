package pl.szejnaArtur.ManagementOfTheCounters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

@Controller
public class CounterController {

    private CounterServiceImpl counterService;
    private PropertyService propertyService;

    @Autowired
    public CounterController(PropertyService propertyService, CounterServiceImpl counterService) {
        this.counterService = counterService;
        this.propertyService = propertyService;
    }

    @RequestMapping(value = "/counter/addCounterPanel", method = RequestMethod.GET)
    public ModelAndView addCounterPanel(ModelAndView mav, @RequestParam("property-id") String propertyId) {
        mav.setViewName("addCounter");
        mav.addObject("propertyId", propertyId);
        return mav;
    }

    @RequestMapping(value = "/counter/add", method = RequestMethod.GET)
    public ModelAndView addPropertyPanel(ModelAndView mav, @RequestParam("name") String name
            ,
                                         @RequestParam("unit") String unit, @RequestParam("price") Double price,
                                         @RequestParam("billingPeriod") Integer billingPeriod,
                                         @RequestParam("firstBillingPeriod") String firstBillingPeriod,
                                         @RequestParam("initialState") Double initialState,
                                         @RequestParam("propertyID") Long propertyID) {

        Property property = propertyService.getProperty(propertyID);
        Counter counter = Counter.of(name, unit, price, billingPeriod, firstBillingPeriod, initialState, property);

        counterService.addCounter(counter);

        mav.setViewName("redirect:/user_panel");
        return mav;
    }
}
