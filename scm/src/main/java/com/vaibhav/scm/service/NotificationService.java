package com.vaibhav.scm.service;

import com.vaibhav.scm.entity.Notifications;
import com.vaibhav.scm.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public Notifications saveNotification(Notifications notifications) {
        return repository.save(notifications);
    }
}
