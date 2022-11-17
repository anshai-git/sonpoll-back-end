package com.sonpoll.oradea.sonpoll.user.controller;

import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

//    @Autowired
//    EmailService emailSender;

//    private final JavaMailSender mailSender;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/resetpass")
    public String resetPassword(@RequestBody CommonRequestDTO<ResetPasswordRequestDTO> request) throws MessagingException, IOException {
        userService.sendResetPassEmail(request.getPayload());
        return "Email sent";
    }


//    @Async
//    private void sendmail() throws AddressException, MessagingException, IOException {
////        Properties props = new Properties();
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.smtp.host", "smtp.gmail.com");
////        props.put("mail.smtp.port", "587");
////
////        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
////            protected PasswordAuthentication getPasswordAuthentication() {
////                return new PasswordAuthentication("fliscadrian23@gmail.com", emailPass);
////            }
////        });
//        Message msg = mailSender.createMimeMessage();
//
//        msg.setFrom(new InternetAddress("fliscadrian23@gmail.com", false));
//
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("fliscadrian23@gmail.com"));
//        msg.setSubject("Pass reset test");
//        msg.setContent("pass reset test", "text/html");
//        msg.setSentDate(new Date());
//
//        MimeBodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setContent("pass reset  test", "text/html");
//
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//        msg.setContent(multipart);
//        Transport.send(msg);
//    }

}
