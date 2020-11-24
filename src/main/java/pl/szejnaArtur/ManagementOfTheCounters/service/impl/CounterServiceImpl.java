package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.CounterRepository;

import java.util.List;

@Service
public class CounterServiceImpl {

    private CounterRepository counterRepository;

    @Autowired
    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public List<Counter> getAllCounters() {
        return counterRepository.findAll();
    }

    public List<Counter> getAllCountersByProperty(Property property){
        return counterRepository.findAllByProperty(property);
    }

}
