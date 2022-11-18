package com.sonpoll.oradea.sonpoll;

import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import com.sonpoll.oradea.sonpoll.user.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.sonpoll.oradea.sonpoll"})
@EnableMongoRepositories
public class SonpollApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTokenRepository userTokenRepository;

    public static void main(String[] args) {
        SpringApplication.run(SonpollApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DB Users: \n ");
        userRepository.findAll().forEach(user -> System.out.println(user.toString()));
        System.out.println("DB Tokens: \n ");
        userTokenRepository.findAll().forEach(user -> System.out.println(user.toString()));
//        CommonResponseDTO succesResponse = CommonResponseDTO.createSuccesResponse(new User());
//        CommonResponseDTO error = CommonResponseDTO.createFailResponse(new CommonError("UNAUTHORIZED", "You don't have acces here "));
//        System.out.println("succesResponse" + succesResponse.toString());
//        sendEmail();

    }

//    public void sendEmail() throws IOException {
//        Email from = new Email("test@example.com");
//        String subject = "Sending with SendGrid is Fun";
//        Email to = new Email("test@example.com");
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }
//    }
}
