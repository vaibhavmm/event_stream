package com.vaibhav.scm.util;

import com.vaibhav.scm.entity.Event;
import com.vaibhav.scm.entity.JobCheck;
import com.vaibhav.scm.repository.EventRepository;
import com.vaibhav.scm.repository.JobCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ScheduledTasks {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JobCheckRepository jobCheckRepository;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    @PersistenceContext
    EntityManager entityManager;

    @Scheduled(fixedRate = 60000)
    public void checkFeedbackgiven() throws ParseException {
        List<JobCheck> jobCheck = new ArrayList<>();
        jobCheck = jobCheckRepository.findByTagAndTimestampLessThanEqual("FDBK_CHK",format.parse(getSqlTimestamp(-15).toString()));
        if(jobCheck!= null){
           for(JobCheck job : jobCheck){
               PushNotification.callApi("Feedback not given for the bill after fifteen minutes",job.getEventid(),"NO_FDBK",true);
               jobCheckRepository.delete(job);
           }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkGreaterValueBills() throws ParseException {
        long latestEventId;
        JobCheck latestjob = jobCheckRepository.findTop1ByTagOrderByEventidDesc("GRTVALUE");
        if (latestjob != null)
            latestEventId = latestjob.getEventid();
        else
            latestEventId = 0;

        List<Long> eventIds = eventRepository.findUseridWithValueGreaterThan(latestEventId, format.parse(getSqlTimestamp(-6).toString()));
        if (eventIds != null && !eventIds.isEmpty()) {
            for (Long id : eventIds) {
                PushNotification.callApi("Bill value more than 20000 in less than 5 minutes", id, "GRTRVALUE", true);
            }
            long maxId =Collections.max(eventIds);
            Event event = eventRepository.findById(maxId).orElse(new Event());
            if (event.getId()!= 0)
                jobCheckRepository.save(new JobCheck(event.getUserid(), event.getId(), "GRTVALUE"));
        }
    }

    public String getSqlTimestamp(int minsOffFromCurrentTime){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,minsOffFromCurrentTime);
        Date date = calendar.getTime();
        return format.format(date);
    }

}
