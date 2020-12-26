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

import java.util.Comparator;
import java.util.List;

@Service
public class CounterServiceImpl {

    private CounterRepository counterRepository;
    private UserRepository userRepository;

    @Autowired
    public CounterServiceImpl(CounterRepository counterRepository, UserRepository userRepository) {
        this.counterRepository = counterRepository;
        this.userRepository = userRepository;
    }

    public List<Counter> getAllCountersByProperty(Property property) {
        return counterRepository.findAllByProperty(property);
    }

    public void addOrUpdateCounter(Counter counter) {
        counterRepository.save(counter);
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

    private void sortMeterStatusByDate(Counter counter){
        List<MeterStatus> meterStatuses = counter.getMeterStatutes();
        meterStatuses.sort(Comparator.comparing(MeterStatus::getDate));
        counter.setMeterStatutes(meterStatuses);
    }
}
