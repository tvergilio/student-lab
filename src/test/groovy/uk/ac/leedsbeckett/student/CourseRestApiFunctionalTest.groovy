package uk.ac.leedsbeckett.student

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification
import uk.ac.leedsbeckett.student.model.Course

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CourseRestApiFunctionalTest extends Specification {

    @Shared
    def restTemplate = new RestTemplate()

    @Shared
    def path = "http://localhost:8094/api/"

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Test get a course by ID returns the correct course'() {

        when: 'a GET request is sent to get a course by id'
        def response = restTemplate.getForEntity(path + 'courses/2', Course.class)

        then: 'the correct response is returned'
        response.status == 200
        response.body.id == 2
        response.body.title == 'AMT'
        response.body.description == 'Advanced Music Theory'
        response.body.fee == 12.0
    }

//    @Sql(['/clear-db.sql', '/insert-courses.sql'])
//    def 'Test get all courses returns all existing courses'() {
//
//        when: 'a GET request is sent to get all courses'
//        def response = restTemplate.getForEntity(path + 'courses', Object[].class)
//
//        then: 'the correct response is returned'
//        response.status == 200
//        response.size == 3
//    }

}
