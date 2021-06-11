package com.revature;

import com.revature.models.Mail;
import com.revature.services.MailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.revature"})
public class MyApplication
{
    public static void main(String[] args)
    {
        Mail mail = new Mail();
        mail.setMailFrom("AlphaCast");
        mail.setMailTo("eobarobest@gmail.com");
        mail.setMailSubject("AlphaCast - Weather Update");
        mail.setMailContent("Hey *Insert User's First Name* !\n Here's is your weather update!\n *insert forcast widget attachment* ");

        SpringApplication.run(MyApplication.class, args);

    }
}
