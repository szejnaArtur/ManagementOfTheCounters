package pl.szejnaArtur.ManagementOfTheCounters.service;

import pl.szejnaArtur.ManagementOfTheCounters.entity.User;

public interface UserService {

    User loadUserByToken(String token);

}
