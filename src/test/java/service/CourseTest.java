package service;

import org.junit.jupiter.api.Test;
import tn.esprit.studentmanagement.entities.Course;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    @Test
    void testCourseSettersAndGetters() {
        Course course = new Course();

        course.setIdCourse(1L);
        course.setName("Java Basics");
        course.setCode("CS101");
        course.setCredit(4);
        course.setDescription("Introduction to Java programming");

        assertEquals(1L, course.getIdCourse());
        assertEquals("Java Basics", course.getName());
        assertEquals("CS101", course.getCode());
        assertEquals(4, course.getCredit());
        assertEquals("Introduction to Java programming", course.getDescription());
    }

    @Test
    void testCourseConstructor() {
        Course course = new Course(
                1L,
                "Web Development",
                "WD202",
                6,
                "HTML, CSS, JS basics",
                null
        );

        assertEquals(1L, course.getIdCourse());
        assertEquals("Web Development", course.getName());
        assertEquals("WD202", course.getCode());
        assertEquals(6, course.getCredit());
        assertEquals("HTML, CSS, JS basics", course.getDescription());
    }
}
