// import com.thbs.lms.exceptionHandler.*;
// import com.thbs.lms.model.Topic;
// import com.thbs.lms.repository.TopicRepository;
// import com.thbs.lms.service.TopicService;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// public class TopicServiceTest {

//     @Mock
//     private TopicRepository topicRepository;

//     @InjectMocks
//     private TopicService topicService;

//     private Topic topic;

//     @BeforeEach
//     void setUp() {
//         topic = new Topic();
//         topic.setTopicName("Test Topic");
//         topic.setDescription("Test Description");
//     }

//     @Test
//     void testGetAllTopics() {
//         List<Topic> expectedTopics = new ArrayList<>();
//         expectedTopics.add(topic);
//         when(topicRepository.findAll()).thenReturn(expectedTopics);

//         List<Topic> actualTopics = topicService.getAllTopics();

//         assertEquals(expectedTopics.size(), actualTopics.size());
//         assertEquals(expectedTopics.get(0), actualTopics.get(0));
//     }

//     @Test
//     void testUpdateDescription() {
//         Long topicId = 1L;
//         String newDescription = "Updated Description";

//         when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
//         when(topicRepository.save(any(Topic.class))).thenReturn(topic);

//         String result = topicService.updateDescription(topicId, newDescription);

//         assertEquals("Description updated successfully.", result);
//         assertEquals(newDescription, topic.getDescription());
//     }

//     @Test
//     void testAddTopicWithValidation() {
//         when(topicRepository.existsByTopicName(anyString())).thenReturn(false);
//         when(topicRepository.save(any(Topic.class))).thenReturn(topic);

//         Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription());

//         assertNotNull(addedTopic);
//         assertEquals(topic, addedTopic);
//     }

//     @Test
//     void testAddTopicWithValidation_DuplicateTopic() {
//         when(topicRepository.existsByTopicName(anyString())).thenReturn(true);

//         assertThrows(DuplicateTopicException.class, () -> {
//             topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription());
//         });
//     }

//     @Test
//     void testAddTopicWithValidation_InvalidData() {
//         assertThrows(InvalidTopicDataException.class, () -> {
//             topicService.addTopicWithValidation(null, null);
//         });
//     }

//     @Test
//     void testUpdateTopicDescriptionWithValidation() {
//         Long topicId = 1L;
//         String newDescription = "Updated Description";

//         when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
//         when(topicRepository.save(any(Topic.class))).thenReturn(topic);

//         Topic updatedTopic = topicService.updateTopicDescriptionWithValidation(topicId, newDescription);

//         assertNotNull(updatedTopic);
//         assertEquals(newDescription, updatedTopic.getDescription());
//     }

//     @Test
//     void testUpdateTopicDescriptionWithValidation_TopicNotFound() {
//         Long topicId = 1L;

//         when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

//         assertThrows(TopicNotFoundException.class, () -> {
//             topicService.updateTopicDescriptionWithValidation(topicId, "Updated Description");
//         });
//     }

//     @Test
//     void testUpdateTopicDescriptionWithValidation_InvalidDescription() {
//         Long topicId = 1L;

//         when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

//         assertThrows(InvalidDescriptionException.class, () -> {
//             topicService.updateTopicDescriptionWithValidation(topicId, null);
//         });
//     }
// }
