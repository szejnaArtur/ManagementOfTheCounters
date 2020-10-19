package pl.szejnaArtur.ManagementOfTheCounters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/sign_up").permitAll()
                .antMatchers("/confirm_email").permitAll()
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/user_panel", true)
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
