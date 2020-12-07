package com.vaibhav.scm.controller;

import com.vaibhav.scm.entity.Notifications;
import com.vaibhav.scm.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cube/scm/pushnotifications")
public class NotificationController {

    public static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private final HttpServletRequest request;

    public NotificationController(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    private NotificationService service;

    @PostMapping
    public ResponseEntity<Notifications> pushEvent(@RequestBody Notifications notifications){
        LOGGER.info("Push Notifications " + Thread.currentThread().getStackTrace()[1].getMethodName());
        String accept = request.getHeader("Accept");
        if(accept!=null && accept.contains("application/json")){
            try{
                return new ResponseEntity<Notifications>(service.saveNotification(notifications), HttpStatus.OK);
            }catch (Exception e){
                LOGGER.error("Couldn't serialize response for content type application/json",e);
                return new ResponseEntity<Notifications>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Notifications>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}