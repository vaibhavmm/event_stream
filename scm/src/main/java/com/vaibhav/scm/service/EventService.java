package com.vaibhav.scm.service;

import com.vaibhav.scm.entity.Event;
import com.vaibhav.scm.entity.JobCheck;
import com.vaibhav.scm.repository.EventRepository;
import com.vaibhav.scm.repository.JobCheckRepository;
import com.vaibhav.scm.util.PushNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private JobCheckRepository jobCheckRepository;

    @Transactional
    public Event saveEvent(Event event) {
        long userId;
        long eventId;
        Event savedEvent = new Event();
        boolean checkFirstBill = repository.existsByUseridAndNounAndVerb(event.getUserid(),"bill","pay");
        savedEvent = repository.saveAndFlush(event);
        userId = savedEvent.getUserid();
        eventId = savedEvent.getId();
        if(!checkFirstBill){
            PushNotification.callApi("first bill pay by " + userId,eventId,"FIRSTBILL",true);
        }
        if(savedEvent.getNoun().equals("bill")){
            jobCheckRepository.save(new JobCheck(userId,eventId,"FDBK_CHK"));
        }else if(savedEvent.getNoun().equals("fdbk")){
            jobCheckRepository.deleteByUseridAndTag(userId,"FDBK_CHK");
        }

        return savedEvent;
    }
}

