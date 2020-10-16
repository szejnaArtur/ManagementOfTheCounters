package pl.szejnaArtur.ManagementOfTheCounters.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.JpaUserDetailsService;

@Component
public class CustomDaoAuthenticationProvider implements AuthenticationProvider {

    private static final String EMAIL_CANNOT_BE_NULL = "Email cannot be null.";
    private static final String CREDENTIALS_CANNOT_BE_NULL = "Credentials cannot be null.";
    private static final String INCORRECT_PASSWORD = "Incorrect password.";

    private JpaUserDetailsService userDetailsService;

    @Autowired
    public CustomDaoAuthenticationProvider(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        Object credentials = authentication.getCredentials();
        Assert.notNull(name, EMAIL_CANNOT_BE_NULL);
        Assert.notNull(credentials, CREDENTIALS_CANNOT_BE_NULL);

        if (credentials instanceof String) {
            return null;
        }
        String password = credentials.toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        boolean passwordMatch = userDetails.getPassword().equals(password);

        if (!passwordMatch) {
            throw new BadCredentialsException(INCORRECT_PASSWORD);
        }

        return new UsernamePasswordAuthenticationToken(name, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
