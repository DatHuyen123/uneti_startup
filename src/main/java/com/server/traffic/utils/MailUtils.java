package com.server.traffic.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MailUtils
 *
 * @author DatDV
 */
@Component
public class MailUtils {

    @Value("${local.path.uploadfile}")
    private String root;

    @Autowired
    private JavaMailSender javaMailSender;

    public Boolean sendMailUseTemplate(String template , Map<String , Object> params , String mailTo , String subject) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message , true , "utf-8");
            helper.setTo(mailTo);
            helper.setSubject(subject);
            helper.setText(template);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e){
            return false;
        }
    }

    public Boolean sendFileToMail(String template , String mailTo , String subject , String pathFile){
        String pathFileForSend = System.getProperty("user.home") + root + pathFile;
        try{
            Message message = javaMailSender.createMimeMessage();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(template);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(pathFileForSend));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
            javaMailSender.send((MimeMessage) message);
            return true;
        } catch (MessagingException | IOException e){
            return false;
        }
    }

    public Boolean sendMultiFileToMail(String template , String mailTo , String subject , List<String> pathFiles){
        try {
            Message message = javaMailSender.createMimeMessage();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(template);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            for (String pathFile : pathFiles) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                String path = System.getProperty("user.home") + root + pathFile;
                attachmentPart.attachFile(new File(path));
                multipart.addBodyPart(attachmentPart);
            }
            message.setContent(multipart);
            javaMailSender.send((MimeMessage) message);
            return true;
        } catch (MessagingException | IOException e) {
            return false;
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
