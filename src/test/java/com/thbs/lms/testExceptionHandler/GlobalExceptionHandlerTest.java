package com.thbs.lms.testExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thbs.lms.exceptionHandler.CourseNotFoundException;
import com.thbs.lms.exceptionHandler.DuplicateCourseException;
import com.thbs.lms.exceptionHandler.DuplicateLearningPlanException;
import com.thbs.lms.exceptionHandler.DuplicateLearningPlanPathException;
import com.thbs.lms.exceptionHandler.DuplicateTopicException;
import com.thbs.lms.exceptionHandler.ErrorResponse;
import com.thbs.lms.exceptionHandler.GlobalExceptionHandler;
import com.thbs.lms.exceptionHandler.InvalidCourseDataException;
import com.thbs.lms.exceptionHandler.InvalidDescriptionException;
import com.thbs.lms.exceptionHandler.InvalidLearningPlanException;
import com.thbs.lms.exceptionHandler.InvalidLearningPlanPathDataException;
import com.thbs.lms.exceptionHandler.InvalidLevelException;
import com.thbs.lms.exceptionHandler.InvalidTopicDataException;
import com.thbs.lms.exceptionHandler.InvalidTrainerException;
import com.thbs.lms.exceptionHandler.LearningPlanNotFoundException;
import com.thbs.lms.exceptionHandler.LearningPlanPathNotFoundException;
import com.thbs.lms.exceptionHandler.TopicNotFoundException;

@ExtendWith(SpringExtension.class)

public class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleLearningPlanNotFoundException() {
        // Create a mock LearningPlanNotFoundException with a custom message
        LearningPlanNotFoundException mockException = mock(LearningPlanNotFoundException.class);
        String errorMessage = "Learning plan not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        // Call the exception handler method
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ErrorResponse response = handler.handleLearningPlanNotFoundException(mockException);

        // Verify the HTTP status and error message
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        // assertEquals("Learning plan not found", response.getMessage());

    }

    @Test
    public void testHandleInvalidLearningPlanException() {
        InvalidLearningPlanException mockException = mock(InvalidLearningPlanException.class);
        String errorMessage = "Invalid learning plan data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidLearningPlanException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid learning plan data", response.getMessage());
    }

    @Test
    public void testHandleDuplicateLearningPlanException() {
        DuplicateLearningPlanException mockException = mock(DuplicateLearningPlanException.class);
        String errorMessage = "Duplicate learning plan detected";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateLearningPlanException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.CONFLICT, response.getStatus());
        // assertEquals("Duplicate learning plan detected", response.getMessage());

    }

    @Test
    public void testHandleDuplicateLearningPlanPathException() {
        DuplicateLearningPlanPathException mockException = mock(DuplicateLearningPlanPathException.class);
        String errorMessage = "Duplicate learning plan path detected";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateLearningPlanPathException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.CONFLICT, response.getStatus());
        // assertEquals("Duplicate learning plan path detected", response.getMessage());
    }

    @Test
    public void testHandleCourseNotFoundException() {
        CourseNotFoundException mockException = mock(CourseNotFoundException.class);
        String errorMessage = "Course not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleCourseNotFoundException(mockException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        // assertEquals("Course not found", response.getMessage());
    }

    @Test
    public void testHandleInvalidCourseDataException() {
        InvalidCourseDataException mockException = mock(InvalidCourseDataException.class);
        String errorMessage = "Invalid course data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidCourseDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid course data", response.getMessage());
    }

    @Test
    public void testHandleInvalidLevelException() {
        InvalidLevelException mockException = mock(InvalidLevelException.class);
        String errorMessage = "Invalid level";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidLevelException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid level", response.getMessage());
    }

    @Test
    public void testHandleDuplicateCourseException() {
        DuplicateCourseException mockException = mock(DuplicateCourseException.class);
        String errorMessage = "Duplicate course found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateCourseException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.CONFLICT, response.getStatus());
        // assertEquals("Duplicate course found", response.getMessage());

    }

    @Test
    public void testHandleTopicNotFoundException() {
        TopicNotFoundException mockException = mock(TopicNotFoundException.class);
        String errorMessage = "Topic not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleTopicNotFoundException(mockException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        // assertEquals("Topic not found", response.getMessage());
    }

    @Test
    public void testHandleInvalidTopicDataException() {
        InvalidTopicDataException mockException = mock(InvalidTopicDataException.class);
        String errorMessage = "Invalid topic data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidTopicDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid topic data", response.getMessage());
    }

    @Test
    public void testHandleInvalidDescriptionException() {
        InvalidDescriptionException mockException = mock(InvalidDescriptionException.class);
        String errorMessage = "Invalid description";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidDescriptionException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid description", response.getMessage());
    }

    @Test
    public void testHandleDuplicateTopicException() {
        DuplicateTopicException mockException = mock(DuplicateTopicException.class);
        String errorMessage = "Duplicate topic found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateTopicException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.CONFLICT, response.getStatus());
        // assertEquals("Duplicate topic found", response.getMessage());
    }

    @Test
    public void testHandleLearningPlanPathNotFoundException() {
        LearningPlanPathNotFoundException mockException = mock(LearningPlanPathNotFoundException.class);
        String errorMessage = "Learning plan path not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleLearningPlanPathNotFoundException(mockException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        // assertEquals("Learning plan path not found", response.getMessage());
    }

    @Test
    public void testHandleInvalidLearningPlanPathDataException() {
        InvalidLearningPlanPathDataException mockException = mock(InvalidLearningPlanPathDataException.class);
        String errorMessage = "Invalid learning plan path data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidLearningPlanPathDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid learning plan path data", response.getMessage());
    }

    @Test
    public void testHandleInvalidTrainerException() {
        InvalidTrainerException mockException = mock(InvalidTrainerException.class);
        String errorMessage = "Invalid trainer";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidTrainerException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());

        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        // assertEquals("Invalid trainer", response.getMessage());
    }

}
