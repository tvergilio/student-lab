package uk.ac.leedsbeckett.student.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class StudentServiceIntegrationTest extends Specification {

    @Autowired
    private StudentService studentService

    @Sql(['/clear-db.sql', '/insert-students.sql'])
    def 'Testing GetStudentJson() reads a student from the database'() {

        when: 'we read the student from the database'
        def result = studentService.getStudentByIdJson(1L)

        then: 'all the attributes are fetched correctly'
        result.content.id == 1L
        result.content.forename == 'Walter'
        result.content.surname == 'White'
        result.content.externalStudentId == 'c7453423'
    }
}
