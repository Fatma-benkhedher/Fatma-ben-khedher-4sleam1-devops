package tn.esprit.studentmanagement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.repositories.CourseRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private tn.esprit.studentmanagement.services.CourseTest courseService;

    @Test
    public void testAddCourse() {
        Course course = new Course();
        course.setName("Math");

        when(courseRepository.save(course)).thenReturn(course);

        Course savedCourse = courseService.addCourse(course);

        assertEquals("Math", savedCourse.getName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testGetAllCourses() {
        Course course = new Course();
        course.setName("Science");

        when(courseRepository.findAll()).thenReturn(Collections.singletonList(course));

        List<Course> courses = courseService.getAllCourses();

        assertEquals(1, courses.size());
        assertEquals("Science", courses.get(0).getName());
        verify(courseRepository, times(1)).findAll();
    }
}
