package com.thbs.lms.testService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.service.TopicService;
import com.thbs.lms.exceptionHandler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testSaveCourse_InvalidCourseDataException() {
        Course course = new Course(); // Create a course with null/empty fields
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_DuplicateCourseException() {
        // Mock data
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setLevel("Test type");
        // Assume this course already exists
        when(courseRepository.findByCourseName("Java Programming")).thenReturn(Optional.of(course));

        // Test
        assertThrows(DuplicateCourseException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_SuccessfulSaving() {
        Course course = new Course();
        course.setCourseName("Python Programming");
        course.setLevel("Test type");
        when(courseRepository.findByCourseName("Python Programming")).thenReturn(Optional.empty());
        when(courseRepository.save(course)).thenReturn(course);
        assertEquals(course, courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_NullCourseName_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName(null);
        course.setLevel("Intermediate");
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_EmptyCourseName_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName("");
        course.setLevel("Intermediate");
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_NullLevel_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName("Java");
        course.setLevel(null);
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_EmptyLevel_InvalidCourseDataException() {
        Course course = new Course();
        course.setCourseName("Java");
        course.setLevel("");
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourses_InvalidCourseDataException() {
        List<Course> courses = new ArrayList<>();
        Course invalidCourse = new Course(); // Create a course object with null or empty values
        courses.add(invalidCourse);

        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_DuplicateCourseException() {
        List<Course> courses = new ArrayList<>();
        Course existingCourse = new Course();
        existingCourse.setCourseName("Java Programming");
        existingCourse.setLevel("Intermediate");
        courses.add(existingCourse);

        when(courseRepository.findByCourseName("Java Programming")).thenReturn(Optional.of(existingCourse));

        assertThrows(DuplicateCourseException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_SuccessfulSaving() {
        List<Course> courses = new ArrayList<>();
        Course newCourse = new Course();
        newCourse.setCourseName("Python Programming");
        newCourse.setLevel("Intermediate");
        courses.add(newCourse);

        when(courseRepository.findByCourseName("Python Programming")).thenReturn(Optional.empty());
        when(courseRepository.save(newCourse)).thenReturn(newCourse);

        List<Course> savedCourses = courseService.saveCourses(courses);

        assertEquals(1, savedCourses.size());
        assertEquals(newCourse, savedCourses.get(0));
    }

    @Test
    void testSaveCourses_InvalidCourseDataException_NullCourseName() {
        // Create a list of courses with one course having null course name
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName(null);
        course1.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseName("Java Programming");
        course2.setLevel("Intermediate");

        courses.add(course1);
        courses.add(course2);

        // Test
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_InvalidCourseDataException_EmptyCourseName() {
        // Create a list of courses with one course having empty course name
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("");
        course1.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseName("Java Programming");
        course2.setLevel("Intermediate");

        courses.add(course1);
        courses.add(course2);

        // Test
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_InvalidCourseDataException_NullLevel() {
        // Create a list of courses with one course having null level
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("Python Programming");
        course1.setLevel(null);

        Course course2 = new Course();
        course2.setCourseName("Java Programming");
        course2.setLevel("Intermediate");

        courses.add(course1);
        courses.add(course2);

        // Test
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_InvalidCourseDataException_EmptyLevel() {
        // Create a list of courses with one course having empty level
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("Python Programming");
        course1.setLevel("");

        Course course2 = new Course();
        course2.setCourseName("Java Programming");
        course2.setLevel("Intermediate");

        courses.add(course1);
        courses.add(course2);

        // Test
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testGetAllCourses_SuccessfulRetrieval() {
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

        when(courseRepository.findAll()).thenReturn(courses);

        // Test
        assertEquals(courses, courseService.getAllCourses());
    }

    @Test
    void testGetCoursesByLevel_InvalidLevelException() {
        // Test with null level
        assertThrows(InvalidLevelException.class, () -> courseService.getCoursesByLevel(null));

        // Test with empty level
        assertThrows(InvalidLevelException.class, () -> courseService.getCoursesByLevel(""));
    }

    @Test
    void testGetCoursesByLevel_SuccessfulRetrieval() {
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
        when(courseRepository.findByLevel(level)).thenReturn(courses);

        // Test
        assertEquals(courses, courseService.getCoursesByLevel(level));
    }

    @Test
    void testGetCourseById_CourseFound() {
        // Mock data
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseID(courseId);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Test
        assertEquals(course, courseService.getCourseById(courseId));
    }

    @Test
    void testGetCourseById_CourseNotFoundException() {
        // Mock behavior to return empty optional (no course found)
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Test
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
    }

    @Test
    void testUpdateCourseName_Success() {
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseID(courseId);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course updatedCourse = courseService.updateCourseName(1L, "New Course Name");

        assertEquals("New Course Name", updatedCourse.getCourseName());

    }

    @Test
    void testUpdateCourseName_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {
            courseService.updateCourseName(1L, "New Course Name");
        });

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testDeleteCourse_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).delete(any(Course.class));
    }

    @Test
    void testDeleteCourse_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {
            courseService.deleteCourse(1L);
        });

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, never()).delete(any(Course.class));
    }

    @Test
    void testDeleteCourses_Success() {
        // Prepare test data
        List<Course> coursesToDelete = new ArrayList<>();
        coursesToDelete.add(new Course(1L, "Course1", "Beginner"));
        coursesToDelete.add(new Course(2L, "Course2", "Intermediate"));

        // Mock behavior of findById and delete methods
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(new Course()));
        doNothing().when(courseRepository).delete(any());

        // Test deleteCourses method
        assertDoesNotThrow(() -> courseService.deleteCourses(coursesToDelete));

        // Verify that delete method is called for each course
        verify(courseRepository, times(2)).delete(any());
    }

    @Test
    void testDeleteCourses_MixOfExistingAndNonExisting() {
        // Prepare test data
        List<Course> coursesToDelete = new ArrayList<>();
        coursesToDelete.add(new Course(1L, "Course1", "Beginner")); // Existing course
        coursesToDelete.add(new Course(2L, "Course2", "Intermediate")); // Non-existing course

        // Mock behavior of findById method
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course())); // Existing course
        when(courseRepository.findById(2L)).thenReturn(Optional.empty()); // Non-existing course

        // Test deleteCourses method
        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class,
                () -> courseService.deleteCourses(coursesToDelete));

        // Verify that CourseNotFoundException is thrown for non-existing course
        assertEquals("Course not found for ID: 2", exception.getMessage());

        // Verify that delete method is called only once for the existing course
        verify(courseRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteCourses_EmptyList() {
        // Test with an empty list of courses
        List<Course> coursesToDelete = new ArrayList<>();

        // No mock behavior needed as no interaction with repository is expected

        // Test deleteCourses method
        assertDoesNotThrow(() -> courseService.deleteCourses(coursesToDelete));
    }

    @Test
    void testDeleteCourses_WhenIdNotFound() {
        // Prepare test data
        List<Course> coursesToDelete = new ArrayList<>();
        coursesToDelete.add(new Course(1L, "Course1", "Beginner"));

        // Mock behavior of findById method to return empty Optional
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Test deleteCourses method
        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class,
                () -> courseService.deleteCourses(coursesToDelete));

        // Verify that CourseNotFoundException is thrown for non-existing course
        assertEquals("Course not found for ID: 1", exception.getMessage());

        // Verify that delete method is not called
        verify(courseRepository, never()).delete(any());
    }
}
