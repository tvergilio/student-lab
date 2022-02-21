package uk.ac.leedsbeckett.student.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification
import uk.ac.leedsbeckett.student.model.Course

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CourseServiceIntegrationTest extends Specification {

    @Autowired
    private CourseService courseService

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Testing GetCourseJson() reads a course from the database'() {

        when: 'we read the Course from the database'
        def result = courseService.getCourseByIdJson(1L)

        then: 'all the attributes are fetched correctly'
        result.content.id == 1L
        result.content.description == 'Software Engineering for Service Computing'
        result.content.fee == 10.0
        result.content.title == 'SESC'
    }

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Testing GetAllCourseJson() reads all courses from the database'() {

        when: 'we read the Course from the database'
        def result = courseService.getAllCoursesJson()

        then: 'all three courses are fetched'
        result.getContent().size() == 3

        and: 'their attributes are as expected'
        result.content
                .collect(EntityModel::getContent)
                .collect(Course::getTitle) == ['SESC', 'AMT', 'PHSC']
    }

    @Sql('/clear-db.sql')
    def 'Testing createNewCourseJson() inserts a new record in the database'() {

        given: 'a new course'
        def course = new Course()
        course.title = 'EHS'
        course.description = 'European History'
        course.fee = 30.50

        when: 'we insert the course in the database'
        def result = courseService.createNewCourseJson(course)

        then: 'the course is persisted'
        def returnedCourse = result.body.content
        returnedCourse.id > 0

        and: 'its attributes are as expected'
        returnedCourse.title == 'EHS'
        returnedCourse.description == 'European History'
        returnedCourse.fee == 30.50
    }

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Testing UpdateCourseJson() modifies a record on the database'() {

        given: 'an existing course from the database'
        def course = courseService.getCourseByIdJson(1).content

        when: 'we modify the course'
        course.description = 'Dummy Course'

        and: 'save the changes to the database'
        def result = courseService.updateCourseJson(1, course)

        then: 'the changes are persisted'
        result.body.content.id == 1
        result.body.content.description == 'Dummy Course'
    }
}
