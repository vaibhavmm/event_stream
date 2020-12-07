package com.vaibhav.scm.repository;

import com.vaibhav.scm.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,Long> {

    Notifications findFirstByRuleTagOrderByEventIdDesc(String ruleTag);
}
