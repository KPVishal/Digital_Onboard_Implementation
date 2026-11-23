package com.bijlipay.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.bijlipay.config.Constants.*;
import static com.bijlipay.config.Constants.LEAD_SOURCE_KVB;

@Component
public class MailHandler {
    @Autowired
    private JavaMailSender javaMailSender;


    public void sendLeadApprovedEmail(String email, String leadNumber,String leadSource) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//        InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
//        message.setRecipients(Message.RecipientType.TO,internetAddresses);
        if(leadSource.equalsIgnoreCase(LEAD_SOURCE_PET_POOJA)){
            InternetAddress[] internetAddresses=InternetAddress.parse("tapaswini.behera@petpooja.com"+","+"muskan.batham@petpooja.com",true);
//            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LEAD_SOURCE_FIDY_PAY)){
            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LS_VIYONA_FINTECH)){
            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LEAD_SOURCE_KVB)){
            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LEAD_SOURCE_WOWPE)){
            InternetAddress[] internetAddresses=InternetAddress.parse("anitha@wowpe.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }
//
//        helper.setTo("dhivya.i@bijlipay.co.in");
        message.setSubject("Lead Approved | "+leadNumber);
        String body ="<body><html><p>Hi Team,</p>\n" +
                "<p>Greetings from Bijlipay!</p>\n<p>There is an update on this lead <b>"+leadNumber+"</b>. Please check the onboarding portal for more information</p>";
        helper.setText(body,true);
//        Resource resource = fileStorageService.loadFileAsResource("Logo.png");
//        helper.addInline("attachment.png", resource);
        String signature = body+ "<p><b>Best Regards,<br/>Bijlipay Team</b></p>" +
//                "<img src = D:/MASTER_ONBOARD/digital/DocumentMoule/uploadDocumentDir/Logo.png>" +
                "</body></html>";
        message.setContent(signature, "text/html");
//        Transport.send(message);
//        helper.setText(body + signature, true);
        javaMailSender.send(message);
    }

    public void sendLeadRejectedEmail(String email, String leadNumber, String leadSource) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//        InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
//        message.setRecipients(Message.RecipientType.TO,internetAddresses);
        if(leadSource.equalsIgnoreCase(LEAD_SOURCE_PET_POOJA)){
            InternetAddress[] internetAddresses=InternetAddress.parse("tapaswini.behera@petpooja.com"+","+"muskan.batham@petpooja.com",true);
//            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LEAD_SOURCE_FIDY_PAY)){
            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LS_VIYONA_FINTECH)){
            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LEAD_SOURCE_KVB)){
            InternetAddress[] internetAddresses=InternetAddress.parse("mrithun.r@bijlipay.co.in"+","+"senthamizhselvan.s@bijlipay.co.in"+","+"arshath.a@bijlipay.co.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }else if (leadSource.equalsIgnoreCase(LEAD_SOURCE_WOWPE)){
            InternetAddress[] internetAddresses=InternetAddress.parse("anitha@wowpe.in",true);
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
        }
//        helper.setTo("yugendran@bijlipay.co.in");
        message.setSubject("Lead Rejected | "+leadNumber);
        String body ="<body><html><p>Hi Team,</p>\n" +
                "<p>Greetings from Bijlipay!</p>\n<p>There is an update on this lead <b>"+leadNumber+"</b>. Please check the onboarding portal for more information</p>";
        helper.setText(body,true);
//        Resource resource = fileStorageService.loadFileAsResource("Logo.png");
//        helper.addInline("attachment.png", resource);
        String signature = body+ "<p><b>Best Regards,<br/>Bijlipay Team</b></p>" +
//                "<img src = D:/MASTER_ONBOARD/digital/DocumentMoule/uploadDocumentDir/Logo.png>" +
                "</body></html>";
        message.setContent(signature, "text/html");
//        Transport.send(message);
//        helper.setText(body + signature, true);
        javaMailSender.send(message);
    }

    public void dailyReport(String fileName) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(SENT_TO_REPORT);
        helper.setSubject("daily Report");
        helper.setFrom("repo@bijlipay.co.in");
        helper.setText("Hi Team,\n\nPlease find the attached document which contains daily report attached.\n\nRegards,\nBijlipay System");
        FileSystemResource file = new FileSystemResource(fileName);
        helper.addAttachment(file.getFilename(), file);
        javaMailSender.send(message);
    }
    public void sendEmails(String subject, String leadNumber) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom("repo@bijlipay.co.in");
        helper.setTo("anitha@wowpe.in");
        helper.setSubject(subject);
        String body ="<body><html><p>Hi Team,</p>\n" +
                "<p>Greetings from Bijlipay!</p>\n<p>There is an update on this lead <b>"+leadNumber+"</b>. Please check the onboarding portal for more information</p>";
        helper.setText(body,true);
        String signature = body+ "<p><b>Best Regards,<br/>Bijlipay Team</b></p>" +
                "</body></html>";
        message.setContent(signature, "text/html");

        javaMailSender.send(message);
    }
}
