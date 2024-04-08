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

    @PostMapping("/add")
    public ResponseEntity<?> addNewTopic(@RequestBody Topic topic) {
        Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(),
                topic.getCourse());
        return ResponseEntity.ok().body(addedTopic);
    }

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    @PatchMapping("/description")
    public ResponseEntity<?> updateDescription(@RequestParam Long topicId, @RequestBody String newDescription) {
        String result = topicService.updateDescription(topicId, newDescription);
        return ResponseEntity.ok().body(result);
    }
}
