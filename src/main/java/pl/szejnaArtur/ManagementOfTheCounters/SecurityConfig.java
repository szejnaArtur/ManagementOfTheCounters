package pl.szejnaArtur.ManagementOfTheCounters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.szejnaArtur.ManagementOfTheCounters.component.CustomDaoAuthenticationProvider;
import pl.szejnaArtur.ManagementOfTheCounters.service.impl.JpaUserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JpaUserDetailsService userDetailsService;

    @Autowired
    private CustomDaoAuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()

                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/send_reset_mail/**").permitAll()
                .antMatchers(HttpMethod.POST, "/reset_credentials").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/sign_up").permitAll()
                .antMatchers("/confirm_email").permitAll()
                .antMatchers("/forgot_password").permitAll()
                .antMatchers("/reset_password/**").permitAll()
                .anyRequest().hasAuthority("USER")
                .and()

                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/property", true)
                .failureUrl("/login?error='Login or password is incorrect.'")

                .and()

                .logout()
                .logoutUrl("/user_logout")
                .logoutSuccessUrl("/login?logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
