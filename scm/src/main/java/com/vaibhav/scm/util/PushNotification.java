package com.vaibhav.scm.util;

import com.vaibhav.scm.entity.Notifications;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PushNotification {

    private static RestTemplate restTemplate = new RestTemplate();

    public static Notifications callApi(String notification, long eventId,String ruleTag,Boolean isNotified){
        Notifications notifications = new Notifications(notification,eventId,ruleTag,isNotified);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Notifications> request =  new HttpEntity<>( notifications,headers);
        ResponseEntity<Notifications> response = restTemplate.postForEntity("http://localhost:8080/cube/scm/pushnotifications",request,Notifications.class);
        return response.getBody();
    }
}
