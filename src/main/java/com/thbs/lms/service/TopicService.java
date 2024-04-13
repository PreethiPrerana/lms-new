package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private static final String NOT_FOUND_MSG = "Topic not found for ID: ";
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    private void validateTopicData(String topicName, String description) {
        if (topicName == null || topicName.isEmpty() || description == null ||
                description.isEmpty()) {
            throw new InvalidTopicDataException("Topic name and description cannot be null or empty.");
        }
    }

    private Topic createTopic(String topicName, String description, Course course) {
        Topic newTopic = new Topic();
        newTopic.setTopicName(topicName);
        newTopic.setDescription(description);
        newTopic.setCourse(course);
        return newTopic;
    }

    public Topic addTopicWithValidation(String topicName, String description,
            Course course) {

        validateTopicData(topicName, description);

        if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
            throw new DuplicateTopicException("Topic '" + topicName + "' already exists for this course.");
        }

        return topicRepository.save(createTopic(topicName, description, course));
    }

    public List<Topic> addTopicsWithValidation(List<Topic> topics) {
        return topics.stream()
                .map(topic -> {
                    validateTopicData(topic.getTopicName(), topic.getDescription());
                    if (topicRepository.existsByTopicNameAndCourse(topic.getTopicName(), topic.getCourse())) {
                        throw new DuplicateTopicException(
                                "Topic '" + topic.getTopicName() + "' already exists for this course.");
                    }
                    return createTopic(topic.getTopicName(), topic.getDescription(), topic.getCourse());
                })
                .collect(Collectors.toList());
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public List<Topic> getTopicsByCourse(Course course) {
        return topicRepository.findByCourse(course);
    }

    private void validateDescription(String newDescription) {
        if (newDescription == null || newDescription.isEmpty()) {
            throw new InvalidDescriptionException("Description cannot be null or empty.");
        }
    }

    public String updateTopicDescriptionWithValidation(Long topicId, String newDescription) {
        return topicRepository.findById(topicId)
                .map(topic -> {
                    validateDescription(newDescription);
                    topic.setDescription(newDescription);
                    topicRepository.save(topic);
                    return "Description updated successfully";
                })
                .orElseThrow(() -> new TopicNotFoundException(NOT_FOUND_MSG + topicId));
    }

    private void validateName(String newName) {
        if (newName == null || newName.isEmpty()) {
            throw new InvalidTopicDataException("Topic Name cannot be null or empty.");
        }
    }

    public String updateTopicNameWithValidation(Long topicId, String newName) {
        return topicRepository.findById(topicId)
                .map(topic -> {
                    validateName(newName);
                    topic.setTopicName(newName);
                    topicRepository.save(topic);
                    return "Topic name updated successfully";
                })
                .orElseThrow(() -> new TopicNotFoundException(NOT_FOUND_MSG + topicId));
    }

    public void deleteTopicById(Long topicId) {
        topicRepository.findById(topicId)
                .ifPresentOrElse(topicRepository::delete,
                        () -> {
                            throw new TopicNotFoundException(NOT_FOUND_MSG + topicId);
                        });
    }

    public void deleteTopics(List<Topic> topics) {
        topics.forEach(topic -> topicRepository.findById(topic.getTopicID())
                .ifPresentOrElse(topicRepository::delete,
                        () -> {
                            throw new TopicNotFoundException(NOT_FOUND_MSG + topic.getTopicID());
                        }));
    }

    public void deleteTopicsByCourse(Course course) {
        topicRepository.findByCourse(course)
                .forEach(topicRepository::delete);
    }
}
