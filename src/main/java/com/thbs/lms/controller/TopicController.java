package com.thbs.lms.controller;

import com.thbs.lms.exceptionHandler.*;
import com.thbs.lms.model.Topic;
import com.thbs.lms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(),
                    topic.getCourse());
            return ResponseEntity.ok().body(addedTopic);
        } catch (DuplicateTopicException | InvalidTopicDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        try {
            List<Topic> topics = topicService.getAllTopics();
            return ResponseEntity.ok().body(topics);
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/description")
    public ResponseEntity<?> updateDescription(@RequestParam Long topicId, @RequestBody String newDescription) {
        try {
            String result = topicService.updateDescription(topicId, newDescription);
            return ResponseEntity.ok().body(result);
        } catch (TopicNotFoundException | InvalidDescriptionException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
