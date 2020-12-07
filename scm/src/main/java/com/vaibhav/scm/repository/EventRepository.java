package com.vaibhav.scm.repository;

import com.vaibhav.scm.EventModel;
import com.vaibhav.scm.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findByUseridAndNoun(Long userId, String noun);

    boolean existsByUseridAndNounAndVerb(long userid,String noun,String verb);

    List<Event> findByTimestampGreaterThanEqual(Date date);

    @Query(value = "select event_id from ( select sum(value) as value,user_id,max(event.id) as event_id from event as event inner join property as property on event.property_id = property.id and event.id > :eventid where event.timestamp >= :date and event.noun = 'bill' group by user_id having count(user_id) >=5 ) as result where value >= 20000",nativeQuery = true)
    List<Long> findUseridWithValueGreaterThan(@Param("eventid") long eventid,@Param("date") Date date);

//    Event findById(long id);

}