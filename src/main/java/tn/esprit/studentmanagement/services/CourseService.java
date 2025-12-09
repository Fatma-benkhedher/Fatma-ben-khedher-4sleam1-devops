package tn.esprit.studentmanagement.services;

import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.repositories.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course addCourse(Course course) {
        return repository.save(course);
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }
}