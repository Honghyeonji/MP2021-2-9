package com.example.mp2021_2_9;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.loader.content.AsyncTaskLoader;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class emailClient {
    private String mailHost = "smtp.gmail.com";
    private Session session;

    public emailClient(String user, String password){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", "587");
        session = Session.getInstance(properties, new EmailAuthenticator(user, password));
    }

    public void sendMailWithFile(String subject, String body, String sender, String recipients, String filePath, String fileName){


        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setSubject(subject);
            message.setContent(body, "text/html;charset=EUC-KR");
            message.setSentDate(new Date());

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.setDataHandler(new DataHandler(new FileDataSource(new File(filePath))));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);
            message.setFileName(fileName);


            Transport transport = session.getTransport("smtp");
            new SendMail().execute(message);
            Log.d("SendMailWithFile : ", "sent");

        }catch (Exception e){
            Log.d("SendMailWithFile : ", "Exception occured :");
            Log.d("SendMailWithFile : ", e.toString());
            Log.d("SendMailWithFile : ", e.getMessage());
        }
    }
}

class EmailAuthenticator extends Authenticator {
    private String id;
    private String password;

    public EmailAuthenticator(String id, String password){
        super();
        this.id = id;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(id, password);
    }
}

class SendMail extends AsyncTask<Message, String, String>{

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Message... messages){
        try {
            Transport.send(messages[0]);
            return "Success";
        }catch (MessagingException e){
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        if(s.equals("Success")){
            Log.d("SendMail : ", "Success");

        }else{
            Log.d("SendMail : ", "Failed");

        }
    }



}
