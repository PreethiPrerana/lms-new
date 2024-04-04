package com.thbs.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

}
