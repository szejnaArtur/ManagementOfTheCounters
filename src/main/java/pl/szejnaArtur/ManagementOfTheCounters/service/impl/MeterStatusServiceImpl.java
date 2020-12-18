package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.entity.MeterStatus;
import pl.szejnaArtur.ManagementOfTheCounters.entity.repository.MeterStatusRepository;

@Service
public class MeterStatusServiceImpl {

    private MeterStatusRepository repository;

    @Autowired
    public MeterStatusServiceImpl(MeterStatusRepository repository){
        this.repository = repository;
    }

    public void save(MeterStatus meterStatus){
        repository.save(meterStatus);
    }

}
