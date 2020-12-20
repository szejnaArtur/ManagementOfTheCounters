package pl.szejnaArtur.ManagementOfTheCounters.service;

import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;

public interface UserService {

    User loadUserByToken(String token);

}
