package tn.esprit.studentmanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Your test class
public class CourseServiceTest {

    private CourseService courseService;

    // Fake repository implementation for testing
    static class FakeCourseRepository implements CourseRepository {
        private List<Course> courses = new ArrayList<>();

        @Override
        public Course save(Course course) {
            courses.add(course);
            return course;
        }

        @Override
        public List<Course> findAll() {
            return courses;
        }

        // Implement other methods if required
    }

    // This method runs before each test
    @BeforeEach
    public void setUp() {
        courseService = new CourseService(new FakeCourseRepository());
    }

    @Test
    public void testAddCourse() {
        Course course = new Course();
        course.setName("Math");

        Course savedCourse = courseService.addCourse(course);

        assertEquals("Math", savedCourse.getName());
    }

    @Test
    public void testGetAllCourses() {
        Course course = new Course();
        course.setName("Science");
        courseService.addCourse(course);

        assertEquals(1, courseService.getAllCourses().size());
    }
}
