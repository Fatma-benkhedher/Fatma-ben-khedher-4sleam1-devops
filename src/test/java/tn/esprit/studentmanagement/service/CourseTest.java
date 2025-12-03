package tn.esprit.studentmanagement.service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.StudentManagementApplication;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.services.CourseService;

import static org.junit.jupiter.api.Assertions.*;


class CourseServiceTest {

    private CourseService courseService;

    @Test
    void testAddCourse() {

        Course course = new Course();
        course.setName("Math");
        courseService.addCourse(course);
        assertEquals(1, courseService.getAllCourses().size());
    }
}
