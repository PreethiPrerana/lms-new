package com.thbs.lms.controller;

import com.thbs.lms.model.Topic;
import com.thbs.lms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<?> addTopic(@RequestBody Topic topic) {
        Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(),
                topic.getCourse());
        return ResponseEntity.ok().body(addedTopic);
    }

    @PostMapping("/addAll")
    public ResponseEntity<?> addTopics(@RequestBody List<Topic> topics) {
        List<Topic> addedTopics = topicService.addTopicsWithValidation(topics);
        return ResponseEntity.ok().body(addedTopics);
    }

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    @PatchMapping("/description/{id}")
    public ResponseEntity<?> updateDescription(@PathVariable Long topicId, @RequestBody String newDescription) {
        String result = topicService.updateTopicDescriptionWithValidation(topicId, newDescription);
        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.notFound().build(); // Or return any appropriate error response
        }
    }
    

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopicById(topicId);
        return ResponseEntity.ok().body("Topic deleted successfully.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTopics(@RequestBody List<Topic> topics) {
        topicService.deleteTopics(topics);
        return ResponseEntity.ok().body("Topics deleted successfully.");
    }
}
