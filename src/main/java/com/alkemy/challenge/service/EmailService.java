package com.alkemy.challenge.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;


public class EmailService {



        public static void sendMail() throws IOException {
            Email from = new Email("federicovinas7@gmail.com");
            Email to = new Email("federicovinas7@gmail.com"); // use your own email address here

            String subject = "Registration to DISNEY API";
            Content content = new Content("text/html", "You've succesfully register to the Disney Character API");

            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getHeaders());
            System.out.println(response.getBody());
        }

}
