package uk.ac.leedsbeckett.student.service

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification
import uk.ac.leedsbeckett.student.model.Course

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CourseServiceTest extends Specification {

    @InjectMocks
    @Autowired
    private CourseService courseService

    @Sql(["/clear-db.sql", "/insert-courses.sql"])
    def "Testing GetCourseJson() reads a course from the database"() {
        when: "we read the Course from the database"
        EntityModel<Course> result = courseService.getCourseByIdJson(1L)

        then: "all the attributes are fetched correctly"
        result.content.getId() == 1L
        result.content.getDescription() == "Software Engineering for Service Computing"
        result.content.getFee() == 10.0
        result.content.getTitle() == "SESC"
    }

    @Sql(["/clear-db.sql", "/insert-courses.sql"])
    def "Testing GetAllCourseJson() reads all courses from the database"() {
        when: "we read the Course from the database"
        CollectionModel<EntityModel<Course>> result = courseService.getAllCoursesJson()

        then: "all three courses are fetched"
        result.getContent().size() == 3

        and: "their attributes are as expected"
        result.getContent()
                .collect(EntityModel::getContent)
                .collect(Course::getTitle) == ["SESC", "AMT", "PHSC"]
    }

    @Sql("/clear-db.sql")
    def "Testing createNewCourseJson() inserts a new record in the database"() {
        given: "a new course"
        Course course = new Course()
        course.setTitle("EHS")
        course.setDescription("European History")
        course.setFee(30.50)

        when: "we insert the course in the database"
        ResponseEntity<EntityModel<Course>> result = courseService.createNewCourseJson(course)

        then: "the course is persisted"
        Course returnedCourse = result.getBody().content
        returnedCourse.id > 0

        and: "its attributes are as expected"
        returnedCourse.getTitle() == "EHS"
        returnedCourse.getDescription() == "European History"
        returnedCourse.getFee() == 30.50
    }
}
