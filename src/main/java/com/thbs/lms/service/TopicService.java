package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public void addNewTopic(String topicName, String description, String resourceTag, Course course) {
        Topic newTopic = new Topic();
        newTopic.setTopicName(topicName);
        newTopic.setDescription(description);
        newTopic.setCourse(course);
        topicRepository.save(newTopic);
    }

    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public String updateDescription(Long topicId, String newDescription) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setDescription(newDescription);
            topicRepository.save(topic);
            return "Description updated successfully.";
        } else {
            throw new IllegalArgumentException("Topic not found for ID: " + topicId);
        }
    }
}
