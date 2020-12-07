package pl.szejnaArtur.ManagementOfTheCounters.component.mailer;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomStringFactory {

    private static final String chars = "qwertyuiopasdfghjklzxcvbnm0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    public static String getRandomString(int lenght) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < lenght; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }
}
