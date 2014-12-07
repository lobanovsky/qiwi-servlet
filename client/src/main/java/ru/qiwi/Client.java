package ru.qiwi;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {

    public static final String URL = "http://localhost:8080/qiwi/clients";

    public static void main(String[] args) throws InterruptedException {
        String newAgt = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>new-agt</request-type>" +
                " <login>8(920)000-11-77</login>" +
                " <password>password</password>" +
                "</request>";


        String agtBal = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>agt-bal</request-type>" +
                " <login>8(920)000-11-22</login>" +
                " <password>password</password>" +
                "</request>";

//        httpPost(newAgt);

        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                executor.execute(new MyRunnable(newAgt));
            } else {
                executor.execute(new MyRunnable(agtBal));
            }
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }


    private static class MyRunnable implements Runnable {

        private String xml;

        public MyRunnable(String xml) {
            this.xml = xml;
        }

        @Override
        public void run() {
            httpPost(xml);
        }
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
