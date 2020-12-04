package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;
import pl.szejnaArtur.ManagementOfTheCounters.repository.PropertyRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getPropertiesByUser(User user){
        return propertyRepository.findAllByUser(user);
    }

    public void addProperty(Property property){
        propertyRepository.save(property);
    }

    public Property getProperty(Long id){
        return propertyRepository.findByPropertyId(id);
    }

}
