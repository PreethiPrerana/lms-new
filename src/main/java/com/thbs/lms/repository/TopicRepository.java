package com.thbs.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;

/**
 * The {@code TopicRepository} interface provides CRUD operations for the
 * {@link com.thbs.lms.model.Topic} entity.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    /**
     * Checks if a topic with the specified name exists for a given course.
     *
     * @param topicName The name of the topic.
     * @param course    The course associated with the topic.
     * @return {@code true} if a topic with the specified name exists for the given
     *         course, {@code false} otherwise.
     */
    boolean existsByTopicNameAndCourse(String topicName, Course course);

    /**
     * Checks if a topic with any name exists.
     *
     * @param anyString Any string value (unused parameter).
     * @return {@code true} if any topic exists, {@code false} otherwise.
     */
    Object existsByTopicName(String anyString);

    /**
     * Checks if a topic with the specified name and description exists for a given
     * course.
     *
     * @param course      The course associated with the topic.
     * @param topicName   The name of the topic.
     * @param description The description of the topic.
     * @return {@code true} if a topic with the specified name and description
     *         exists for the given course, {@code false} otherwise.
     */
    boolean existsByCourseAndTopicNameAndDescription(Course course, String topicName, String description);

    /**
     * Retrieves a list of topics associated with a given course.
     *
     * @param course The course for which to retrieve topics.
     * @return A list of topics associated with the given course.
     */
    List<Topic> findByCourse(Course course);
}
