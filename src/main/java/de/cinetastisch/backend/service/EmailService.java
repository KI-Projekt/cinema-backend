package de.cinetastisch.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;


@Service
public class EmailService {
    private JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    public void sendTicket(String to, String film, String date, String time, int ticketCount) throws Exception{
        Context context = new Context();

        context.setVariable("film",film);
        context.setVariable("date",date);
        context.setVariable("time",time);
        context.setVariable("ticketCount",ticketCount);

        String body = templateEngine.process("src/main/resources/EmailTemplates/TicketMail.html",context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =new MimeMessageHelper(message);
        helper.setTo(to);
        helper.setSubject("Cinetastisch Entertainment - Ticket reservation");
        helper.setText(body,true);
        mailSender.send(message);

    }

    public void registration(String to, String firstname, String lastname) throws Exception{
        Context context = new Context();

        context.setVariable("firstname",firstname);
        context.setVariable("lastname",lastname);


        String body = templateEngine.process("src/main/resources/EmailTemplates/RegistrationMail.html",context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =new MimeMessageHelper(message);
        helper.setTo(to);
        helper.setSubject("Cinetastisch Entertainment - Successful registration");
        helper.setText(body,true);
        mailSender.send(message);

    }


}
