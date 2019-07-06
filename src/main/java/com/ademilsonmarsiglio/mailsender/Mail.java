package com.ademilsonmarsiglio.mailsender;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

/**
 *
 * @author ademilsonmarsiglio
 */
public class Mail {
    
    String smtp, user, password;
    Integer port;
    TransportStrategy strategy;
    String fromName, fromEmail;
    
    public void sendEmails(String text, String subject, To... to) {
        for (To para : to) {
            sendEmail(text, para.getName(), para.getEmail(), subject, true);
        }
    }

    public boolean sendEmail(String text, String name, String to, String subject, boolean async) {
        
        Email email = EmailBuilder
                .startingBlank()
                .from(fromName, fromEmail)
                .to(name, to)
                .withSubject(subject)
                .withHTMLText(text)
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer(smtp, port, user, password)
                .withTransportStrategy(strategy)
                .withSessionTimeout(10 * 1000)
                .buildMailer();
                
        try {
            mailer.sendMail(email, async);
        } catch (Exception e) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, "Erro ao enviar Email", e);
            return false;
        }

        return true;
    }
    
    public static class To {
        private String name;
        private String email;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email the email to set
         */
        public void setEmail(String email) {
            this.email = email;
        }        
    }
}