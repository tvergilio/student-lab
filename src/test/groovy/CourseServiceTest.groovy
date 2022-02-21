package uk.ac.leedsbeckett.student.service

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
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
    def setup() {

    }

    def "Testing GetCourseJson() reads a course from the database"() {
        when: "we read the Course from the database"
        EntityModel<Course> result = courseService.getCourseByIdJson(1L)
        then: "all the attributes are fetched correctly"
        result.content.getId() == 1L
        result.content.getDescription() == "Software Engineering for Service Computing"
        result.content.getFee() == 10.0
        result.content.getTitle() == "SESC"
    }
}
