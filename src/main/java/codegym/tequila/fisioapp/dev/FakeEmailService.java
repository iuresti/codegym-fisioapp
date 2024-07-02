package codegym.tequila.fisioapp.dev;

import codegym.tequila.fisioapp.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class FakeEmailService implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(FakeEmailService.class);

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        logger.info("Sending email to <{}>, subject <{}>, body: <{}>", to, subject, text);
    }
}
