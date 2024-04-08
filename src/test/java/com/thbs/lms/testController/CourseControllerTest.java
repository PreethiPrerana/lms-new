package com.thbs.lms.testController;

import com.thbs.lms.controller.CourseController;
import com.thbs.lms.model.Course;
import com.thbs.lms.service.CourseService;
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
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void testAddCourse() {
        // Given
        Course course = new Course();

        when(courseService.saveCourse(course)).thenReturn(course);

        // When
        ResponseEntity<?> responseEntity = courseController.addCourse(course);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(course, responseEntity.getBody());
        verify(courseService, times(1)).saveCourse(course);
    }

    @Test
    void testGetAllCourses() {
        // Given
        List<Course> courses = new ArrayList<>();

        when(courseService.getAllCourses()).thenReturn(courses);

        // When
        ResponseEntity<?> responseEntity = courseController.getAllCourses();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(courses, responseEntity.getBody());
        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetCoursesByLevel() {
        // Given
        String level = "Intermediate";
        List<Course> courses = new ArrayList<>();

        when(courseService.getCoursesByLevel(level)).thenReturn(courses);

        // When
        ResponseEntity<?> responseEntity = courseController.getCoursesByLevel(level);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(courses, responseEntity.getBody());
        verify(courseService, times(1)).getCoursesByLevel(level);
    }

    @Test
    void testGetCourseById() {
        // Given
        Long id = 1L;
        Course course = new Course();

        when(courseService.getCourseById(id)).thenReturn(course);

        // When
        ResponseEntity<?> responseEntity = courseController.getCourseById(id);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(course, responseEntity.getBody());
        verify(courseService, times(1)).getCourseById(id);
    }
}
