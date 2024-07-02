package codegym.tequila.fisioapp.dev;

import codegym.tequila.fisioapp.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Profile("dev")
public class FakeEmailService implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(FakeEmailService.class);

    @Override
    @Async
    public void sendSimpleEmail(String to, String subject, String text) {

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        logger.info("Sending email to <{}>, subject <{}>, body: <{}>", to, subject, text);
    }
}
