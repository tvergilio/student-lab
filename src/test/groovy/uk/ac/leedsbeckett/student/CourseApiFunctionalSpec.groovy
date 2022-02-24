package uk.ac.leedsbeckett.student

import groovyx.net.http.RESTClient
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles('test')
class CourseApiFunctionalSpec extends Specification {
    def path = 'http://localhost:8094/api/'
    def client

    def setup() {
        client = new RESTClient(path, MediaType.APPLICATION_JSON)
    }

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Test GET a course by ID returns the correct course'() {

        when: 'a GET request is sent to get a course by id'
        def response = client.get(path: 'courses/2')

        then: 'the correct response is returned'
        with(response) {
            status == 200
            data.id == 2
            data.title == 'AMT'
            data.description == 'Advanced Music Theory'
            data.fee == 12.0
            data._links.containsKey 'self'
            data._links.containsKey 'courses'
        }
    }

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Test GET all courses returns all the courses in the database'() {

        when: 'a GET request is sent to get all courses'
        def response = client.get(path: 'courses')

        then: 'the correct response is returned'
        with(response) {
            status == 200
            def courses = data._embedded.courseList
            courses.size == 3
            courses.collect { c -> c.title } == ['SESC', 'AMT', 'PHSC']
            courses.collect { c -> c.description } == ['Software Engineering for Service Computing',
                                                       'Advanced Music Theory', 'Philosophy of Science']
            courses.collect { c -> c.fee } == [10.0, 12.0, 20.0]
            courses.collect { x -> x._links }.size() == 3
            data._links.containsKey 'self'
        }
    }

    @Sql('/clear-db.sql')
    def 'Test POST a course creates a new course on the database'() {

        given: 'a JSON representation of a course'
        def courseJson = /
            {
              "title": "MHS",
              "description": "Medieval History",
              "fee": 25.5
            }/

        when: 'a POST request is sent to the courses endpoint'
        def response = client.post(path: 'courses', body: courseJson)

        then: 'the course is created and the correct response is returned'
        with(response) {
            status == 201
            data.id > 0
            data.title == 'MHS'
            data.description == 'Medieval History'
            data.fee == 25.5
            data._links.containsKey 'self'
            data._links.containsKey 'courses'
        }
    }

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Test PUT a course updates an existing course on the database'() {

        given: 'the desired modifications on an existing course'
        def courseJson = /
            {
              "title": "POL",
              "description": "Political Theory",
              "fee": 54.2
            }/

        when: 'a PUT request is sent to the API to modify the course with ID = 1'
        def response = client.put(path: 'courses/1', body: courseJson)

        then: 'the course is updated and the correct response is returned'
        with(response) {
            status == 200
            data.id == 1
            data.title == 'POL'
            data.description == 'Political Theory'
            data.fee == 54.2
            data._links.containsKey 'self'
            data._links.containsKey 'courses'
        }
    }
}
