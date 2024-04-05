// package com.thbs.lms.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.thbs.lms.model.Topic;
// import com.thbs.lms.service.TopicService;

// import java.util.List;

// @RestController
// @RequestMapping("/topics")
// public class TopicController {

//     @Autowired
//     private TopicService topicService;

//     @PostMapping("/add")
//     public Topic addNewTopic(@RequestBody Topic topic) {
//         return topicService.saveTopic(topic);
//     }

//     @GetMapping
//     public List<Topic> getAllTopics() {
//         return topicService.getAllTopics();
//     }

//     @PatchMapping("/description")
//     public String updateDescription(@RequestParam Long topicId, @RequestBody String newDescription) {
//         return topicService.updateDescription(topicId, newDescription);
//     }

// }
package com.thbs.lms.controller;

import com.thbs.lms.exceptionHandler.*;
import com.thbs.lms.model.Topic;
import com.thbs.lms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/add")
    public ResponseEntity<Topic> addNewTopic(@RequestBody Topic topic) {
        try {
            Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(), topic.getCourse());
            return ResponseEntity.ok().body(addedTopic);
        } catch (DuplicateTopicException | InvalidTopicDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RepositoryOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        try {
            List<Topic> topics = topicService.getAllTopics();
            return ResponseEntity.ok().body(topics);
        } catch (RepositoryOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PatchMapping("/description")
    public ResponseEntity<String> updateDescription(@RequestParam Long topicId, @RequestBody String newDescription) {
        try {
            String result = topicService.updateDescription(topicId, newDescription);
            return ResponseEntity.ok().body(result);
        } catch (TopicNotFoundException | InvalidDescriptionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RepositoryOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
