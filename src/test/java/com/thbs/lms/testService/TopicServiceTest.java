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
    void testAddTopics_Success() {
        // Mock repository behavior
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class))).thenReturn(false);
        when(topicRepository.save(any(Topic.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Create a list of valid topics
        List<Topic> topics = new ArrayList<>();
        topicService.addTopicWithValidation("Topic1", "Description1", new Course());
        topicService.addTopicWithValidation("Topic2", "Description2", new Course());

        // Assert that topics are added successfully without any exceptions
        assertDoesNotThrow(() -> {
            topicService.addTopicsWithValidation(topics);
        });
    }

    @Test
    void testAddTopicsWithValidation_ValidTopics() {
        // Arrange
        List<Topic> topics = new ArrayList<>();
        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);
        Topic validTopic2 = new Topic();
        validTopic2.setTopicName("Topic 2");
        validTopic2.setDescription("Description 2");
        validTopic2.setCourse(course);
        topics.add(validTopic1);
        topics.add(validTopic2);
        when(topicRepository.existsByTopicNameAndCourse(any(), any())).thenReturn(false);

        // Act
        List<Topic> addedTopics = assertDoesNotThrow(() -> {
            return topicService.addTopicsWithValidation(topics);
        });

        // Assert
        assertEquals(topics.size(), addedTopics.size());
    }

    @Test
    void testAddTopicsWithValidation_DuplicateTopics() {
        // Arrange
        List<Topic> topics = new ArrayList<>();
        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);
        Topic duplicateTopic = new Topic();
        duplicateTopic.setTopicName("Topic 1");
        duplicateTopic.setDescription("Description 1");
        duplicateTopic.setCourse(course);
        topics.add(validTopic1);
        topics.add(duplicateTopic);
        when(topicRepository.existsByTopicNameAndCourse(any(), any())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateTopicException.class, () -> {
            topicService.addTopicsWithValidation(topics);
        });
    }

    @Test
    void testAddTopicsWithValidation_InvalidTopics() {
        // Arrange
        List<Topic> topics = new ArrayList<>();
        Topic invalidTopic = new Topic(); // Empty topic
        topics.add(invalidTopic);

        // Act & Assert
        assertThrows(InvalidTopicDataException.class, () -> {
            topicService.addTopicsWithValidation(topics);
        });
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

    // @Test
    // void testGetAllTopics_Exception() {
    // // Mock repository to throw an exception
    // when(topicRepository.findAll()).thenThrow(new RuntimeException("Database
    // error"));

    // // Call the service method and expect an exception
    // assertThrows(RepositoryOperationException.class, () ->
    // topicService.getAllTopics());
    // }

    @Test
    void testUpdateDescription_Success() {
        // Mock repository to return an optional topic
        Topic topic = new Topic();
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        // Call the service method
        String result = topicService.updateTopicDescriptionWithValidation(1L, "New description");

        // Assert the result
        assertEquals("Description updated successfully", result);
        assertEquals("New description", topic.getDescription());
    }

    @Test
    void testUpdateDescription_TopicNotFoundException() {
        // Mock repository to return an empty optional
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect a TopicNotFoundException
        assertThrows(TopicNotFoundException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, "New description"));
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
    void testAddTopicWithValidation_EmptyTopicName() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation("", "Description", new Course()));
    }

    @Test
    void testAddTopicWithValidation_NullTopicName() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation(null, "Description", new Course()));
    }

    @Test
    void testAddTopicWithValidation_EmptyDescription() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation("New Tpoic", "", new Course()));
    }

    @Test
    void testAddTopicWithValidation_NullDescription() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation("New Tpoic", null, new Course()));
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
    void testUpdateTopicDescriptionWithValidation_Success() {
        // Mock repository to return an optional containing the topic
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        // Mock repository to save the topic
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        // Call the service method
        String updatedTopic = topicService.updateTopicDescriptionWithValidation(1L, "New Description");

        // Assert the result
        assertEquals("Description updated successfully", updatedTopic);
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
    void testUpdateTopicDescriptionWithValidation_NullDescription() {
        // Mock repository to return an optional containing the topic
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        // Call the service method with an empty description and expect an
        // InvalidDescriptionException
        assertThrows(InvalidDescriptionException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, null));
    }

    // @Test
    // void testUpdateDescription_ExceptionHandling() {
    // // Mocking data
    // Long topicId = 1L;
    // String newDescription = "New Description";
    // Topic topic = new Topic();
    // topic.setTopicID(topicId);

    // // Mocking behavior
    // when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
    // when(topicRepository.save(topic)).thenThrow(new RuntimeException("Mocked
    // exception"));

    // // Verify exception handling
    // RepositoryOperationException exception =
    // assertThrows(RepositoryOperationException.class, () -> {
    // topicService.updateTopicDescriptionWithValidation(topicId, newDescription);
    // });

    // assertEquals("Error updating description: Mocked exception",
    // exception.getMessage());
    // }

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

    @Test
    void testDeleteTopicById_Success() {
        // Mock repository to return an optional containing the topic
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        // Call the service method
        assertDoesNotThrow(() -> {
            topicService.deleteTopicById(1L);
        });

        // Verify that delete method of repository is called
        verify(topicRepository, times(1)).delete(topic);
    }

    @Test
    void testDeleteTopicById_TopicNotFoundException() {
        // Mock repository to return an empty optional (topic not found) when findById
        // is called with 1L
        when(topicRepository.findById(eq(1L))).thenReturn(Optional.empty());

        // Call the service method and expect a TopicNotFoundException
        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class, () -> {
            topicService.deleteTopicById(1L);
        });

        // Verify the exception message
        assertEquals("Topic not found for ID: 1", exception.getMessage());

        // Verify that findById method of repository is called with the correct argument
        verify(topicRepository).findById(eq(1L));

        // Verify that delete method of repository is not called
        verify(topicRepository, never()).delete(any());
    }

    @Test

    void testDeleteTopics_TopicNotFoundException() {
        // Mock repository behavior to return empty Optional when findById is called
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        List<Topic> topicsToDelete = new ArrayList<Topic>();
        Topic topic1 = new Topic();
        topic1.setTopicID(1L);
        topicsToDelete.add(topic1);

        Topic topic2 = new Topic();
        topic2.setTopicID(1L);
        topicsToDelete.add(topic2);

        // Call the service method and expect a TopicNotFoundException
        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class, () -> {
            topicService.deleteTopics(topicsToDelete);
        });

        // Assert the exception message if necessary
        assertEquals("Topic not found for ID: 1", exception.getMessage()); // Replace <topicId> with the actual
                                                                           // topic ID
    }

    @Test
    void testDeleteTopics() {
        // Arrange
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        topic1.setTopicID(1L);
        Topic topic2 = new Topic();
        topic2.setTopicID(2L);
        topics.add(topic1);
        topics.add(topic2);
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic1));
        when(topicRepository.findById(2L)).thenReturn(Optional.of(topic2));

        // Act
        assertDoesNotThrow(() -> {
            topicService.deleteTopics(topics);
        });

        // Assert
        verify(topicRepository, times(1)).delete(topic1);
        verify(topicRepository, times(1)).delete(topic2);
    }

    @Test
    void testDeleteTopicsByCourse_Success() {
        // Mock repository to return topics for the course
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic());
        topics.add(new Topic());
        when(topicRepository.findByCourse(any(Course.class))).thenReturn(topics);

        // Call the service method
        assertDoesNotThrow(() -> topicService.deleteTopicsByCourse(new Course()));

        // Verify that delete method is called for each topic
        verify(topicRepository, times(2)).delete(any(Topic.class));
    }

    @Test
    void testDeleteTopicsByCourse_NoTopicsFound() {
        // Mock repository to return an empty list of topics
        when(topicRepository.findByCourse(any(Course.class))).thenReturn(new ArrayList<>());

        // Call the service method
        assertDoesNotThrow(() -> topicService.deleteTopicsByCourse(new Course()));

        // Verify that delete method is not called
        verify(topicRepository, never()).delete(any(Topic.class));
    }

}
