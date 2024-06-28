package codegym.tequila.fisioapp.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Test
    void sendSimpleEmail() {
        // Given:
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailServiceImpl emailService = new EmailServiceImpl(mailSender);
        String to = "to";
        String subject = "subject";
        String text = "text";
        ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // When:
        emailService.sendSimpleEmail(to, subject, text);

        // Then:
        verify(mailSender).send(simpleMailMessageArgumentCaptor.capture());
        SimpleMailMessage simpleMailMessage = simpleMailMessageArgumentCaptor.getValue();

        assertThat(simpleMailMessage.getTo()).containsExactly(to);
        assertThat(simpleMailMessage.getSubject()).isEqualTo(subject);
        assertThat(simpleMailMessage.getText()).isEqualTo(text);

        verifyNoMoreInteractions(mailSender);

    }
}
