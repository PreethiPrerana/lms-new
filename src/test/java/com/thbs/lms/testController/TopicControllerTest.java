package com.thbs.lms.testController;

import com.thbs.lms.controller.TopicController;
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
    public void testAddTopic() {
        Topic topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setDescription("Test Description");

        when(topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(), topic.getCourse()))
                .thenReturn(topic);

        ResponseEntity<?> responseEntity = topicController.addTopic(topic);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(topic, responseEntity.getBody());
    }

    @Test
    public void testAddTopics() {
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        topic1.setTopicName("Test Topic 1");
        topic1.setDescription("Test Description 1");
        Topic topic2 = new Topic();
        topic2.setTopicName("Test Topic 2");
        topic2.setDescription("Test Description 2");
        topics.add(topic1);
        topics.add(topic2);

        when(topicService.addTopicsWithValidation(topics)).thenReturn(topics);

        ResponseEntity<?> responseEntity = topicController.addTopics(topics);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(topics, responseEntity.getBody());
    }

    @Test
    public void testGetAllTopics() {
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        topic1.setTopicName("Test Topic 1");
        topic1.setDescription("Test Description 1");
        Topic topic2 = new Topic();
        topic2.setTopicName("Test Topic 2");
        topic2.setDescription("Test Description 2");
        topics.add(topic1);
        topics.add(topic2);

        when(topicService.getAllTopics()).thenReturn(topics);

        ResponseEntity<?> responseEntity = topicController.getAllTopics();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(topics, responseEntity.getBody());
    }

    @Test
    public void testUpdateDescription() {
        Long topicId = 1L;
        String newDescription = "Updated Description";

        when(topicService.updateTopicDescriptionWithValidation(topicId, newDescription))
                .thenReturn("Description updated successfully");

        ResponseEntity<?> responseEntity = topicController.updateDescription(topicId, newDescription);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Description updated successfully", responseEntity.getBody());
    }

    @Test
    public void testDeleteTopic() {
        Long topicId = 1L;

        ResponseEntity<?> responseEntity = topicController.deleteTopic(topicId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Topic deleted successfully.", responseEntity.getBody());
        verify(topicService, times(1)).deleteTopicById(topicId);
    }

    @Test
    public void testDeleteTopics() {
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        topic1.setTopicID(1L);
        Topic topic2 = new Topic();
        topic2.setTopicID(2L);
        topics.add(topic1);
        topics.add(topic2);

        ResponseEntity<?> responseEntity = topicController.deleteTopics(topics);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Topics deleted successfully.", responseEntity.getBody());
        verify(topicService, times(1)).deleteTopics(topics);
    }
}
