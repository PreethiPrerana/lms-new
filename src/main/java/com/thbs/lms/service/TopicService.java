package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.exceptionHandler.*;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        try {
            return topicRepository.findAll();
        } catch (Exception e) {
            throw new RepositoryOperationException("Error retrieving topics: " + e.getMessage());
        }
    }

    public String updateDescription(Long topicId, String newDescription) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setDescription(newDescription);
            try {
                topicRepository.save(topic);
                return "Description updated successfully.";
            } catch (Exception e) {
                throw new RepositoryOperationException("Error updating description: " + e.getMessage());
            }
        } else {
            throw new TopicNotFoundException("Topic not found for ID: " + topicId);
        }
    }

    public Topic addTopicWithValidation(String topicName, String description, Course course) {
        // Check for duplicate topic
        if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
            throw new DuplicateTopicException("Topic '" + topicName + "' already exists for this course.");
        }

        // Validate topic data
        if (topicName == null || topicName.isEmpty() || description == null || description.isEmpty()) {
            throw new InvalidTopicDataException("Topic name and description cannot be null or empty.");
        }

        Topic newTopic = new Topic();
        newTopic.setTopicName(topicName);
        newTopic.setDescription(description);
        newTopic.setCourse(course);
        return topicRepository.save(newTopic);
    }

    public Topic updateTopicDescriptionWithValidation(Long topicId, String newDescription) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            // Validate new description
            if (newDescription == null || newDescription.isEmpty()) {
                throw new InvalidDescriptionException("Description cannot be null or empty.");
            }

            topic.setDescription(newDescription);
            return topicRepository.save(topic);
        } else {
            throw new TopicNotFoundException("Topic not found for ID: " + topicId);
        }
    }
}
