package com.vaibhav.scm.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String notification;
    @Column(name = "event_id")
    private long eventId;
    @Column(name ="rule_tag")
    private String ruleTag; // 1. FIRSTBILL 2. GRTRVALUE 3.NO_FDBK
    @Column(name = "is_notified")
    private Boolean isNotified;
    @CreationTimestamp
    private Date timestamp;

    public Notifications(String notification, long eventId, String ruleTag, Boolean isNotified) {
        this.notification = notification;
        this.eventId = eventId;
        this.ruleTag = ruleTag;
        this.isNotified = isNotified;
    }
}
