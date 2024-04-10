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
    public void testAddCourse() {
        Course course = new Course();
        course.setCourseName("Test Course");
        course.setLevel("Intermediate");

        when(courseService.saveCourse(course)).thenReturn(course);

        ResponseEntity<?> responseEntity = courseController.addCourse(course);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(course, responseEntity.getBody());
    }

    @Test
    public void testAddCourses() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("Test Course 1");
        course1.setLevel("Intermediate");
        Course course2 = new Course();
        course2.setCourseName("Test Course 2");
        course2.setLevel("Advanced");
        courses.add(course1);
        courses.add(course2);

        when(courseService.saveCourses(courses)).thenReturn(courses);

        ResponseEntity<?> responseEntity = courseController.addCourses(courses);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(courses, responseEntity.getBody());
    }

    @Test
    public void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("Test Course 1");
        course1.setLevel("Intermediate");
        Course course2 = new Course();
        course2.setCourseName("Test Course 2");
        course2.setLevel("Advanced");
        courses.add(course1);
        courses.add(course2);

        when(courseService.getAllCourses()).thenReturn(courses);

        ResponseEntity<?> responseEntity = courseController.getAllCourses();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(courses, responseEntity.getBody());
    }

    @Test
    public void testGetCoursesByLevel() {
        String level = "Intermediate";
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("Test Course 1");
        course1.setLevel(level);
        Course course2 = new Course();
        course2.setCourseName("Test Course 2");
        course2.setLevel(level);
        courses.add(course1);
        courses.add(course2);

        when(courseService.getCoursesByLevel(level)).thenReturn(courses);

        ResponseEntity<?> responseEntity = courseController.getCoursesByLevel(level);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(courses, responseEntity.getBody());
    }

    @Test
    public void testGetCourseById() {
        Long id = 1L;
        Course course = new Course();
        course.setCourseID(id);
        course.setCourseName("Test Course");
        course.setLevel("Intermediate");

        when(courseService.getCourseById(id)).thenReturn(course);

        ResponseEntity<?> responseEntity = courseController.getCourseById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(course, responseEntity.getBody());
    }

    @Test
    public void testDeleteCourses() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseID(1L);
        Course course2 = new Course();
        course2.setCourseID(2L);
        courses.add(course1);
        courses.add(course2);

        ResponseEntity<?> responseEntity = courseController.deleteCourses(courses);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Courses deleted successfully", responseEntity.getBody());
        verify(courseService, times(1)).deleteCourses(courses);
    }
}
