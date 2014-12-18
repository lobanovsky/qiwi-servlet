package ru.qiwi;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;

public class Client {

    public static final String URL = "http://localhost:8080/qiwi/clients";

    public static void main(String[] args) throws InterruptedException {
        final String newAgt = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>new-agt</request-type>" +
                " <login>8(920)000-11-77</login>" +
                " <password>password</password>" +
                "</request>";


        final String agtBal = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>agt-bal</request-type>" +
                " <login>8(920)000-11-22</login>" +
                " <password>password</password>" +
                "</request>";

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                httpPost(newAgt);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                httpPost(agtBal);
            }
        });

        t1.start();
        t2.start();
    }


    private static void httpPost(String xml) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(URL);
        try {
            postMethod.setRequestEntity(new StringRequestEntity(xml, "application/xml", "UTF8"));
            int statusCode = client.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + postMethod.getStatusLine());
            }
            byte[] responseBody = postMethod.getResponseBody();
            System.out.println(new String(responseBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
