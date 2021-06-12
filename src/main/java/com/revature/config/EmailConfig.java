

package com.revature.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class EmailConfig {
    private static final String Host;
    private static final int Port;
    private static final String Username;
    private static final String Password;


    static {
        Host = System.getenv("SPRING_EMAIL_HOST");
        Port = Integer.parseInt(System.getenv("SPRING_EMAIL_PORT"));
        Username = System.getenv("SPRING_EMAIL_USERNAME");
        Password = System.getenv("SPRING_EMAIL_PASSWORD");
    }



    public String getHost() {
        return Host;
    }

    public int getPort() {
        return Port;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        System.out.println(""+Host+"\n"+Port+"\n"+Username+"\n"+Password);
        mailSender.setHost(Host);
        mailSender.setPort(Port);

        mailSender.setUsername(Username);
        mailSender.setPassword(Password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        mailSender.setJavaMailProperties(props);

        return mailSender;

    }
//    @Bean
//    public SpringTemplateEngine springTemplateEngine(){
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addTemplateResolver(htmlTemplateResolver());
//        return templateEngine;
//    }
//
//    @Bean
//    public SpringResourceTemplateResolver htmlTemplateResolver(){
//        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
//        emailTemplateResolver.setPrefix("resources/static/");
//        emailTemplateResolver.setSuffix(".html");
//        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
//        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        return  emailTemplateResolver;
//    }

}
