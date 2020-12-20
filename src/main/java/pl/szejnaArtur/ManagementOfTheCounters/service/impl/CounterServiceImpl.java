package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Property;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.CounterRepository;

import java.util.List;

@Service
public class CounterServiceImpl {

    private CounterRepository counterRepository;

    @Autowired
    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public List<Counter> getAllCountersByProperty(Property property) {
        return counterRepository.findAllByProperty(property);
    }

    public void addOrUpdateCounter(Counter counter) {
        counterRepository.save(counter);
    }

    public Counter getCounter(Long id) {
        return counterRepository.findByCounterId(id);
    }
}
