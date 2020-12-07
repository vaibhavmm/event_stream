package com.vaibhav.scm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_check")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userid;
    @Column(name = "event_id")
    private long eventid;
    private String tag;
    @CreationTimestamp
    private Date timestamp;

    public JobCheck(long userid, long eventid, String tag) {
        this.userid = userid;
        this.eventid = eventid;
        this.tag = tag;
    }
}

