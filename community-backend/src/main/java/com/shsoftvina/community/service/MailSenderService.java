package com.shsoftvina.community.service;

import com.shsoftvina.community.properties.MailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class MailSenderService {

    private final Logger log = LoggerFactory.getLogger(MailSenderService.class);

    private static final String DATA_MAIL = "dataMail";

    private static final String BASE_URL = "baseUrl";

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${community.server.base-url}")
    private String server;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
                "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart,
                isHtml,
                to,
                subject,
                content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(mailProperties.getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(String email, Object dataMail, String templateName, String titleKey, Locale locale) {
        if (email == null) {
            log.debug("Email doesn't exist'");
            return;
        }

        Context context = new Context(locale);
        context.setVariable(DATA_MAIL, dataMail);
        context.setVariable(BASE_URL, server);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendEmail(email, subject, content, false, true);
    }
}