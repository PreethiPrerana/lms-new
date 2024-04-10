package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.exceptionHandler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic addTopicWithValidation(String topicName, String description,
            Course course) {

        if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
            throw new DuplicateTopicException("Topic '" + topicName + "' already exists for this course.");
        }

        if (topicName == null || topicName.isEmpty() || description == null ||
                description.isEmpty()) {
            throw new InvalidTopicDataException("Topic name and description cannot be null or empty.");
        }

        Topic newTopic = new Topic();
        newTopic.setTopicName(topicName);
        newTopic.setDescription(description);
        newTopic.setCourse(course);
        return topicRepository.save(newTopic);
    }

    public List<Topic> addTopicsWithValidation(List<Topic> topics) {
        List<Topic> addedTopics = new ArrayList<>();
        for (Topic topic : topics) {
            String topicName = topic.getTopicName();
            String description = topic.getDescription();
            Course course = topic.getCourse();

            if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
                throw new DuplicateTopicException("Topic '" + topicName + "' already existsfor this course.");
            }

            if (topicName == null || topicName.isEmpty() || description == null ||
                    description.isEmpty()) {
                throw new InvalidTopicDataException("Topic name and description cannot be null or empty.");
            }

            Topic newTopic = new Topic();
            newTopic.setTopicName(topicName);
            newTopic.setDescription(description);
            newTopic.setCourse(course);

            addedTopics.add(topicRepository.save(newTopic));
        }
        return addedTopics;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public String updateTopicDescriptionWithValidation(Long topicId, String newDescription) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            if (newDescription == null || newDescription.isEmpty()) {
                throw new InvalidDescriptionException("Description cannot be null or empty.");
            }

            topic.setDescription(newDescription);
            topicRepository.save(topic);
            return "Description updated successfully";
        } else {
            throw new TopicNotFoundException("Topic not found for ID: " + topicId);
        }
    }

    public void deleteTopicById(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            topicRepository.delete(optionalTopic.get());
        } else {
            throw new TopicNotFoundException("Topic not found for ID: " + topicId);
        }
    }

    public void deleteTopics(List<Topic> topics) {
        for (Topic topic : topics) {
            Long topicId = topic.getTopicID();
            Optional<Topic> optionalTopic = topicRepository.findById(topicId);
            if (optionalTopic.isPresent()) {
                topicRepository.delete(topic);
            } else {
                throw new TopicNotFoundException("Topic not found for ID: " + topicId);
            }
        }
    }

    public void deleteTopicsByCourse(Course course) {
        List<Topic> topics = topicRepository.findByCourse(course);
        for (Topic topic : topics) {
            topicRepository.delete(topic);
        }
    }
}
