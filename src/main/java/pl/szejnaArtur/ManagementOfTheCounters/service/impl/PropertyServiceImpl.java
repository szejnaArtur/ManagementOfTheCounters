package pl.szejnaArtur.ManagementOfTheCounters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Property;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.PropertyRepository;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.repository.UserRepository;
import pl.szejnaArtur.ManagementOfTheCounters.service.PropertyService;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private UserRepository userRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public List<Property> getPropertiesByUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        return propertyRepository.findAllByUser(user);
    }

    public void addProperty(Property property) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName).get();
        property.setUser(user);

        propertyRepository.save(property);
    }

    public Property getProperty(Long id) {
        List<Property> properties = getPropertiesByUser();
        Optional<Property> optionalProperty = properties.stream().filter(i -> i.getPropertyId().equals(id)).findFirst();
        return optionalProperty.orElse(null);
    }

    public void updateProperty(Property property, Long propertyId) {
        Property newProperty = getProperty(propertyId);
        if (newProperty != null){
            newProperty.updateProperty(property);
            propertyRepository.save(newProperty);
        } else {
            throw new NullPointerException("There is no property associated with this user");
        }
    }
}
