package com.thbs.lms.testService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.exceptionHandler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testSaveCourse_InvalidCourseDataException() {
        Course course = new Course(); // Create a course with null/empty fields
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_DuplicateCourseException() {
        // Mock data
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setLevel("Test type");
        // Assume this course already exists
        Mockito.when(courseRepository.findByCourseName("Java Programming")).thenReturn(Optional.of(course));

        // Test
        assertThrows(DuplicateCourseException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_SuccessfulSaving() {
        Course course = new Course();
        course.setCourseName("Python Programming");
        course.setLevel("Test type");
        Mockito.when(courseRepository.findByCourseName("Python Programming")).thenReturn(Optional.empty());
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        assertEquals(course, courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_RepositoryOperationException() {
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setLevel("Test type");
        Mockito.when(courseRepository.findByCourseName("Java Programming")).thenReturn(Optional.empty());
        Mockito.when(courseRepository.save(course)).thenThrow(new RuntimeException("Database connection failed"));
        assertThrows(RepositoryOperationException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_NullCourseName_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName(null);
        course.setLevel("Intermediate");
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_EmptyCourseName_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName("");
        course.setLevel("Intermediate");
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_NullLevel_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName("Java");
        course.setLevel(null);
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testSaveCourse_EmptyLevel_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName("Java");
        course.setLevel("");
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    public void testGetAllCourses_SuccessfulRetrieval() {
        // Mock data
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCourseID(1L);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseID(2L);
        course2.setCourseName("Python");
        course2.setLevel("Beginner");

        courses.add(course);
        courses.add(course2);

        Mockito.when(courseRepository.findAll()).thenReturn(courses);

        // Test
        assertEquals(courses, courseService.getAllCourses());
    }

    @Test
    public void testGetAllCourses_RepositoryOperationException() {
        // Mock behavior to throw an exception
        Mockito.when(courseRepository.findAll()).thenThrow(new RuntimeException("Database connection failed"));

        // Test
        assertThrows(RepositoryOperationException.class, () -> courseService.getAllCourses());
    }

    @Test
    public void testGetCoursesByLevel_InvalidLevelException() {
        // Test with null level
        assertThrows(InvalidLevelException.class, () -> courseService.getCoursesByLevel(null));

        // Test with empty level
        assertThrows(InvalidLevelException.class, () -> courseService.getCoursesByLevel(""));
    }

    @Test
    public void testGetCoursesByLevel_SuccessfulRetrieval() {
        // Mock data
        String level = "Intermediate";
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCourseID(1L);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseID(2L);
        course2.setCourseName("Python");
        course2.setLevel("Intermediate");

        courses.add(course);
        courses.add(course2);
        Mockito.when(courseRepository.findByLevel(level)).thenReturn(courses);

        // Test
        assertEquals(courses, courseService.getCoursesByLevel(level));
    }

    @Test
    public void testGetCoursesByLevel_RepositoryOperationException() {
        // Mock behavior to throw an exception
        String level = "Intermediate";
        Mockito.when(courseRepository.findByLevel(level)).thenThrow(new RuntimeException("Database connection failed"));

        // Test
        assertThrows(RepositoryOperationException.class, () -> courseService.getCoursesByLevel(level));
    }

    @Test
    public void testGetCourseById_CourseFound() {
        // Mock data
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseID(courseId);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Test
        assertEquals(course, courseService.getCourseById(courseId));
    }

    @Test
    public void testGetCourseById_CourseNotFoundException() {
        // Mock behavior to return empty optional (no course found)
        Long courseId = 1L;
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Test
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
    }
}
