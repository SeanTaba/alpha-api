package com.revature.services;

import com.revature.models.SMS;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URI;

@Component
public class SMSService {
    private static final String ACCOUNT_SID;
    private static final String AUTH_TOKEN;
    private static final String FROM_NUMBER;



    static {
        ACCOUNT_SID = System.getenv("ACCOUNT_SID");
        AUTH_TOKEN = System.getenv("AUTH_TOKEN");
        FROM_NUMBER = System.getenv("FROM_NUMBER");
    }



    public static void send(SMS sms) {
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER),sms.getMessage())
                .create();

        System.out.println("here is my id: "+message.getSid());
    }


    public void receive(MultiValueMap<String, String> map) {
    }
}
