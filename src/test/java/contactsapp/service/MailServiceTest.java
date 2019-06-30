package contactsapp.service;

import contactsapp.core.entity.MailParam;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MailServiceTest {
    private static final MailParam mailParams = new MailParam(Arrays.asList("noxijoi@gmail.com"), "simple message", "message");


    @Test
    void testSendMessage() {
        MailService mailService = new MailService();
        mailService.sendMessage(mailParams);
    }
}