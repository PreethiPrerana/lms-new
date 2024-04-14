package com.thbs.lms.controller;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@CrossOrigin("*")
public class TopicController {

    private final TopicService topicService;
    private final CourseService courseService;

    @Autowired
    public TopicController(TopicService topicService, CourseService courseService) {
        this.topicService = topicService;
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(),
                topic.getCourse());
        return ResponseEntity.ok().body(addedTopic);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<Topic>> addTopics(@RequestBody List<Topic> topics) {
        List<Topic> addedTopics = topicService.addTopicsWithValidation(topics);
        return ResponseEntity.ok().body(addedTopics);
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Topic>> getTopicsByCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);

        List<Topic> topics = topicService.getTopicsByCourse(course);
        return ResponseEntity.ok().body(topics);
    }

    @PutMapping("/name/{topicId}")
    public ResponseEntity<String> updateName(@PathVariable Long topicId, @RequestBody String newName) {
        String result = topicService.updateTopicNameWithValidation(topicId, newName);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/description/{topicId}")
    public ResponseEntity<String> updateDescription(@PathVariable Long topicId, @RequestBody String newDescription) {
        String result = topicService.updateTopicDescriptionWithValidation(topicId, newDescription);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopicById(topicId);
        return ResponseEntity.ok().body("Topic deleted successfully.");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteTopics(@RequestBody List<Topic> topics) {
        topicService.deleteTopics(topics);
        return ResponseEntity.ok().body("Topics deleted successfully.");
    }
}
