package br.com.will.GerenciaUsers.service;

import br.com.will.GerenciaUsers.model.mail.EmailDetails;
import br.com.will.GerenciaUsers.model.mail.EmailMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailMethods {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public String simpleMail(EmailDetails emailDetails) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMsgBody());
            simpleMailMessage.setTo(emailDetails.getRecipient());

            javaMailSender.send(simpleMailMessage);
            return "Email enviado";
        }catch (Exception ex){
            throw new RuntimeException("Erro ao enviar email");
        }
    }
}
