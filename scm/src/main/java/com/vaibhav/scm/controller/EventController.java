package com.vaibhav.scm.controller;

import com.vaibhav.scm.entity.Event;
import com.vaibhav.scm.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cube/scm/events")
public class EventController {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
    private final HttpServletRequest request;

    @Autowired
    private EventService service;


    public EventController(HttpServletRequest request) {
        this.request = request;
    }

    @PostMapping("/save")
    public ResponseEntity<Event> pushEvent(@RequestBody Event event){
        LOGGER.info("Events  " + Thread.currentThread().getStackTrace()[1].getMethodName());
        String accept = request.getHeader("Accept");
        if(accept!=null && accept.contains("application/json")){
            try{
                return new ResponseEntity<Event>(service.saveEvent(event), HttpStatus.OK);
            }catch (Exception e){
                LOGGER.error("Couldn't serialize response for content type application/json",e);
                return new ResponseEntity<Event>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Event>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
