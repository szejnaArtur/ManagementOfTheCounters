package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.MeterStatus;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Property;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.CounterRepository;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CounterServiceImpl {

    private CounterRepository counterRepository;
    private UserRepository userRepository;
    private PropertyService propertyService;
    private MeterStatusServiceImpl meterStatusService;

    @Autowired
    public CounterServiceImpl(CounterRepository counterRepository, UserRepository userRepository,
                              PropertyService propertyService, MeterStatusServiceImpl meterStatusService) {
        this.counterRepository = counterRepository;
        this.userRepository = userRepository;
        this.propertyService = propertyService;
        this.meterStatusService = meterStatusService;
    }

    public List<Counter> getAllCountersByProperty(Property property) {
        return counterRepository.findAllByProperty(property);
    }

    public void addCounter(Counter counter, Long propertyId) {

        Property property = propertyService.getProperty(propertyId);
        if (property != null){
            counter.setProperty(property);
            counterRepository.save(counter);
        } else {
            throw new NullPointerException("There is no property associated with this user");
        }
    }

    public void updateCounter(Counter counter) {

        Counter newCounter = getCounter(counter.getCounterId());
        if (newCounter != null){
            newCounter.updateCounter(counter);
            counterRepository.save(newCounter);
        } else {
            throw new NullPointerException("There is no counter associated with this user");
        }
    }

    public Counter getCounter(Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = user.getProperties();
        for (Property property : properties) {
            List<Counter> counters = property.getCounters();
            for (Counter counter : counters) {
                if (counter.getCounterId().equals(id)) {
                    Counter thisCounter = counterRepository.findByCounterId(id);
                    sortMeterStatusByDate(thisCounter);

                    return thisCounter;
                }
            }
        }
        return null;
    }

    public List<Counter> getAllCounters(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        List<Property> properties = user.getProperties();
        List<Counter> counters = new ArrayList<>();
        for (Property property : properties) {
            counters.addAll(property.getCounters());
        }
        return counters;
    }

    public Optional<Counter> findById(Long id){
        return counterRepository.findById(id);
    }

    private void sortMeterStatusByDate(Counter counter){
        List<MeterStatus> meterStatuses = counter.getMeterStatutes();
        meterStatuses.sort(Comparator.comparing(MeterStatus::getDate));
        counter.setMeterStatutes(meterStatuses);
    }

    public void delete(Long id) {
        counterRepository.deleteById(id);
    }
}
