package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.MeterStatus;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.MeterStatusRepository;

import java.util.Optional;

@Service
public class MeterStatusServiceImpl {

    private MeterStatusRepository meterStatusRepository;

    @Autowired
    public MeterStatusServiceImpl(MeterStatusRepository repository) {
        this.meterStatusRepository = repository;
    }

    public void saveOrUpdate(MeterStatus meterStatus) {
        meterStatusRepository.save(meterStatus);
    }

    public void delete(MeterStatus meterStatus) {
        meterStatusRepository.delete(meterStatus);
    }

    public Optional<MeterStatus> findById(Long meterStatusId) {
        return meterStatusRepository.findById(meterStatusId);
    }
}
