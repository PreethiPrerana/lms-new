package com.thbs.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.Topic;
import com.thbs.lms.service.TopicService;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/add")
    public Topic addNewTopic(@RequestBody Topic topic) {
        return topicService.saveTopic(topic);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PatchMapping("/description")
    public String updateDescription(@RequestParam Long topicId, @RequestBody String newDescription) {
        return topicService.updateDescription(topicId, newDescription);
    }

}
