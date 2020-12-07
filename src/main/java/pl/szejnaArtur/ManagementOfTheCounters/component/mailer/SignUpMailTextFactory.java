package pl.szejnaArtur.ManagementOfTheCounters.component.mailer;

import org.springframework.stereotype.Component;

@Component
public class SignUpMailTextFactory {

    private static final String SUBJECT = "Confirmation link";
    private static final String TEXT = "In order to activate your account enter:";
    private static final String LINK = "http://localhost:8080/confirm_email?token=";

    String getConfirmationMailSubject() {
        return SUBJECT;
    }

    String getConfirmationMailText(String token) {
        return TEXT + LINK + token;
    }


}
