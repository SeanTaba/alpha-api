package com.revature;

import com.revature.dtos.EmailInfoDTO;
import com.revature.models.Mail;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import com.revature.services.MailService;
import com.revature.services.MailServiceImpl;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.revature"})
public class MyApplication implements ApplicationRunner {
    @Autowired
    private UserService userService;
    @Autowired
    MailServiceImpl mailService;

    public static void main(String[] args) {
        System.out.println("we made it here");
        SpringApplication.run(MyApplication.class, args);

    }

    @Override
    //@Scheduled(cron = "0 30 9 ? * MON")
    public void run(ApplicationArguments args) throws Exception {
        Mail mail = new Mail();
        List<User> subscriptionList = userService.getAllUsers();
        for (User subscribedUser : subscriptionList) {
            System.out.println(subscribedUser.getWantsWeeklyUpdates());
            try {
                if (subscribedUser.getWantsWeeklyUpdates() == null) {
                    continue;
                }else if(subscribedUser.getWantsWeeklyUpdates())
                    mail.setMailFrom("AlphaCast");
                    mail.setMailTo(subscribedUser.getEmail());
                    mail.setMailSubject("AlphaCast - Weather Update");
                    //                mail.setMailContent(emailInfo.getEmailContent());
//                    Map<String, Object> model = new HashMap<>();
//                    model.put("name", subscribedUser.getFirstName());
//                    model.put("location", subscribedUser.getCity());
//                    model.put("sign", "Yours weatherly,\n" +
//                            "-The AlphaCast Team");
//                    mail.setProps(model);
                    mail.setMailContent("Hi " + subscribedUser.getFirstName() +" !\n" +
                            "\tYour weekly forecast report for "+subscribedUser.getCity()+" is now ready !\n" +
                            "-Alpha Cast Team");
                    try {
                        mailService.sendEmail(mail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


}