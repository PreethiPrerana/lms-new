package com.thbs.lms.controller;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code TopicController} class handles HTTP requests related to topics.
 * It provides endpoints for adding, retrieving, updating, and deleting topics.
 */
@RestController
@RequestMapping("/topic")
@CrossOrigin("172.18.4.186:5173, 172.18.4.113:5173, 172.18.4.195:5173")
public class TopicController {

    /**
     * The service responsible for handling business logic related to topics.
     */
    private final TopicService topicService;

    /**
     * The service responsible for handling business logic related to courses.
     */
    private final CourseService courseService;

    /**
     * Constructs a new {@code TopicController} with the specified services.
     *
     * @param topicService  the topic service
     * @param courseService the course service
     */
    @Autowired
    public TopicController(TopicService topicService, CourseService courseService) {
        this.topicService = topicService;
        this.courseService = courseService;
    }

    /**
     * Adds a single topic.
     *
     * @param topic the topic to add
     * @return a response entity containing the added topic
     */
    @PostMapping
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(),
                topic.getCourse());
        return ResponseEntity.ok().body(addedTopic);
    }

    /**
     * Adds multiple topics.
     *
     * @param topics the topics to add
     * @return a response entity containing a list of added topics
     */
    @PostMapping("/multiple")
    public ResponseEntity<List<Topic>> addTopics(@RequestBody List<Topic> topics) {
        List<Topic> addedTopics = topicService.addTopicsWithValidation(topics);
        return ResponseEntity.ok().body(addedTopics);
    }

    /**
     * Retrieves all topics.
     *
     * @return a response entity containing a list of all topics
     */
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    /**
     * Retrieves a topic by its ID.
     *
     * @param topicId the ID of the topic to retrieve
     * @return a response entity containing the retrieved topic
     */
    @GetMapping("/id/{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        return ResponseEntity.ok().body(topic);
    }

    /**
     * Retrieves all topics related to a course.
     *
     * @param courseId the ID of the course
     * @return a response entity containing a list of topics related to the
     *         specified course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Topic>> getTopicsByCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<Topic> topics = topicService.getTopicsByCourse(course);
        return ResponseEntity.ok().body(topics);
    }

    /**
     * Updates the name of a topic given its ID.
     *
     * @param topicId the ID of the topic to update
     * @param newName the new name for the topic
     * @return a response entity indicating the success of the update operation
     */
    @PutMapping("/name/{topicId}")
    public ResponseEntity<String> updateName(@PathVariable Long topicId, @RequestBody String newName) {
        String result = topicService.updateTopicNameWithValidation(topicId, newName);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Updates the description of a topic given its ID.
     *
     * @param topicId        the ID of the topic to update
     * @param newDescription the new description for the topic
     * @return a response entity indicating the success of the update operation
     */
    @PutMapping("/description/{topicId}")
    public ResponseEntity<String> updateDescription(@PathVariable Long topicId, @RequestBody String newDescription) {
        String result = topicService.updateTopicDescriptionWithValidation(topicId, newDescription);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Deletes a topic by its ID.
     *
     * @param topicId the ID of the topic to delete
     * @return a response entity indicating the success of the deletion operation
     */
    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopicById(topicId);
        return ResponseEntity.ok().body("Topic deleted successfully.");
    }

    /**
     * Deletes multiple topics.
     *
     * @param topics the topics to delete
     * @return a response entity indicating the success of the deletion operation
     */
    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteTopics(@RequestBody List<Topic> topics) {
        topicService.deleteTopics(topics);
        return ResponseEntity.ok().body("Topics deleted successfully.");
    }
}
