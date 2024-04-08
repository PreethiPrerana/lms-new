package com.thbs.lms.testService;

import com.thbs.lms.exceptionHandler.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    private Topic topic;

    @BeforeEach
    void setUp() {
        topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setDescription("Test Description");
    }

    @Test
    void testGetAllTopics_Success() {
        // Mock repository to return a list of topics
        List<Topic> expectedTopics = new ArrayList<>();
        expectedTopics.add(new Topic());
        when(topicRepository.findAll()).thenReturn(expectedTopics);

        // Call the service method
        List<Topic> actualTopics = topicService.getAllTopics();

        // Assert the result
        assertEquals(expectedTopics.size(), actualTopics.size());
    }

    @Test
    void testGetAllTopics_Exception() {
        // Mock repository to throw an exception
        when(topicRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Call the service method and expect an exception
        assertThrows(RepositoryOperationException.class, () -> topicService.getAllTopics());
    }

    @Test
    void testUpdateDescription_Success() {
        // Mock repository to return an optional topic
        Topic topic = new Topic();
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        // Call the service method
        String result = topicService.updateDescription(1L, "New description");

        // Assert the result
        assertEquals("Description updated successfully.", result);
        assertEquals("New description", topic.getDescription());
    }

    @Test
    void testUpdateDescription_RepositoryOperationException() {
        // Mock repository to throw an exception
        when(topicRepository.findById(1L)).thenThrow(new RepositoryOperationException("Database error"));

        // Call the service method and expect an exception
        assertThrows(RepositoryOperationException.class, () -> topicService.updateDescription(1L, "New description"));
    }

    @Test
    void testUpdateDescription_TopicNotFoundException() {
        // Mock repository to return an empty optional
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect a TopicNotFoundException
        assertThrows(TopicNotFoundException.class, () -> topicService.updateDescription(1L, "New description"));
    }

    @Test
    void testAddTopicWithValidation_Success() {
        // Mock repository to return false (topic does not exist)
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class))).thenReturn(false);
        // Mock repository to save the topic
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        // Call the service method
        Topic addedTopic = topicService.addTopicWithValidation("New Topic", "Description", new Course());

        // Assert the result
        assertNotNull(addedTopic);
        assertEquals(topic, addedTopic);
    }

    @Test
    void testAddTopicWithValidation_DuplicateTopic() {
        // Mock repository to return true (topic already exists)
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class))).thenReturn(true);

        // Call the service method and expect a DuplicateTopicException
        assertThrows(DuplicateTopicException.class,
                () -> topicService.addTopicWithValidation("Existing Topic", "Description", new Course()));
    }

    @Test
    void testAddTopicWithValidation_InvalidData() {
        // Call the service method with invalid data and expect an
        // InvalidTopicDataException
        assertThrows(InvalidTopicDataException.class, () -> topicService.addTopicWithValidation(null, null, null));
    }

    @Test
    void testAddTopicWithValidation_RepositoryOperationException() {
        // Mock repository to throw an exception
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class)))
                .thenThrow(new RepositoryOperationException("Database error"));

        // Call the service method and expect a RepositoryOperationException
        assertThrows(RepositoryOperationException.class,
                () -> topicService.addTopicWithValidation("New Topic", "Description", new Course()));
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_Success() {
        // Mock repository to return an optional containing the topic
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        // Mock repository to save the topic
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        // Call the service method
        Topic updatedTopic = topicService.updateTopicDescriptionWithValidation(1L, "New Description");

        // Assert the result
        assertNotNull(updatedTopic);
        assertEquals("New Description", updatedTopic.getDescription());
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_InvalidDescription() {
        // Mock repository to return an optional containing the topic
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        // Call the service method with an empty description and expect an
        // InvalidDescriptionException
        assertThrows(InvalidDescriptionException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, ""));
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_RepositoryOperationException() {
        // Mock repository to throw an exception
        when(topicRepository.findById(anyLong())).thenThrow(new RepositoryOperationException("Database error"));

        // Call the service method and expect a RepositoryOperationException
        assertThrows(RepositoryOperationException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, "New Description"));
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_TopicNotFoundException() {
        // Mock repository to return an empty optional (topic not found)
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the service method and expect a TopicNotFoundException
        assertThrows(TopicNotFoundException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, "New Description"));
    }
}
