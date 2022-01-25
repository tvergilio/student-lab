package uk.ac.leedsbeckett.studentlab.service;

import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.studentlab.model.Course;
import uk.ac.leedsbeckett.studentlab.model.CourseRepository;

import java.util.List;

@Component
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

}
