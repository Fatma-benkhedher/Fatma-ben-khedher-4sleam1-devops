package service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.services.CourseService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Test
    void testAddCourse() {
        Course course = new Course();
        course.setName("Java Basics");
        course.setCode("CS101");
        course.setCredit(4);
        course.setDescription("Introduction to Java");

        Course saved = courseService.addCourse(course);

        assertNotNull(saved.getIdCourse());
        assertEquals("Java Basics", saved.getName());
    }

    @Test
    void testGetAllCourses() {
        courseService.addCourse(new Course(null, "Spring Boot", "CS102", 5, "Intro Spring", null));
        assertFalse(courseService.getAllCourses().isEmpty());
    }
}