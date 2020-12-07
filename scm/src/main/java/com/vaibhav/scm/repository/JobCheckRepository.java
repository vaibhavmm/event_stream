package com.vaibhav.scm.repository;

import com.vaibhav.scm.entity.JobCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobCheckRepository extends JpaRepository<JobCheck,Long> {
    List<JobCheck> findByTagAndTimestampLessThanEqual(String tag, Date timestamp);

    void deleteByUseridAndTag(long userid,String tag);

    JobCheck findTop1ByTagOrderByEventidDesc(String tag);
}
