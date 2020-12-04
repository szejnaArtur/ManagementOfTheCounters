package pl.szejnaArtur.ManagementOfTheCounters.service;

import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;

public interface PropertyService {

    void addProperty(Property property);

    Property getProperty(Long propertyID);
}
