package pl.szejnaArtur.ManagementOfTheCounters.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.CounterServiceImpl;

@Component
public class CustomDaoAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    private static final String USERNAME_CANNOT_BE_NULL = "Username cannot be null";
    private static final String CREDENTIALS_CANNOT_BE_NULL = "Credentials cannot be null";
    private static final String INCORRECT_PASSWORD = "Incorrect password";

    @Autowired
    public CustomDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        Object credentials = authentication.getCredentials();
        Assert.notNull(name, USERNAME_CANNOT_BE_NULL);
        Assert.notNull(credentials, CREDENTIALS_CANNOT_BE_NULL);

        String password = credentials.toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        boolean passwordMatch = passwordEncoder.matches(password, userDetails.getPassword());

        if (!passwordMatch) {
            throw new BadCredentialsException(INCORRECT_PASSWORD);
        }

        if (!userDetails.isEnabled()) {
            throw new BadCredentialsException(INCORRECT_PASSWORD);
        }

        return new UsernamePasswordAuthenticationToken(name, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
