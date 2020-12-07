package pl.szejnaArtur.ManagementOfTheCounters.component.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SignUpMailer {

    private JavaMailSender mailSender;
    private SignUpMailTextFactory textFactory;

    @Autowired
    public SignUpMailer(JavaMailSender mailSender, SignUpMailTextFactory textFactory) {
        this.mailSender = mailSender;
        this.textFactory = textFactory;
    }

    public void sandMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendConfirmationLink(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(textFactory.getConfirmationMailSubject());
        message.setText(textFactory.getConfirmationMailText(token));
        mailSender.send(message);
    }
}
