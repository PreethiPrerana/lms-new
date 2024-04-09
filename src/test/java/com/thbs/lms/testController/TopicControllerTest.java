package com.thbs.lms.testController;

import com.thbs.lms.controller.TopicController;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicControllerTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    @Test
    void testAddNewTopic() {
        // Given
        Topic topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setDescription("Test Description");
        topic.setCourse(new Course());

        when(topicService.addTopicWithValidation(anyString(), anyString(), any(Course.class)))
                .thenReturn(topic);

        // When
        ResponseEntity<?> responseEntity = topicController.addTopic(topic);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(topic, responseEntity.getBody());
        verify(topicService, times(1)).addTopicWithValidation(anyString(), anyString(), any(Course.class));
    }

    @Test
    void testGetAllTopics() {
        // Given
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        topic1.setTopicName("Test Topic 1");
        topic1.setDescription("Test Description 1");
        topic1.setCourse(new Course());
        topics.add(topic1);

        when(topicService.getAllTopics()).thenReturn(topics);

        // When
        ResponseEntity<?> responseEntity = topicController.getAllTopics();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(topics, responseEntity.getBody());
        verify(topicService, times(1)).getAllTopics();
    }

    @Test
    void testUpdateDescription() {
        // Given
        Long topicId = 1L;
        String newDescription = "Updated Description";
        String expectedResult = "Description updated successfully.";

        when(topicService.updateTopicDescriptionWithValidation(topicId, newDescription)).thenReturn(expectedResult);

        // When
        ResponseEntity<?> responseEntity = topicController.updateDescription(topicId, newDescription);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(topicService, times(1)).updateTopicDescriptionWithValidation(topicId, newDescription);
    }
}
