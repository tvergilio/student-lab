package uk.ac.leedsbeckett.student


import geb.spock.GebSpec
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles('test')
class CourseApplicationFunctionalSpec extends GebSpec {

    def setupSpec() {
        System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\WebDrivers\\geckodriver.exe")
    }

    @Sql(['/clear-db.sql', '/insert-courses.sql'])
    def 'Getting a list of courses'() {

        when: 'I go to the course list page'
        browser.drive {
            go 'http://localhost:8094/courses'
        }

        then: 'I land on the right page'
        title == 'Course List'

        and: 'It has the correct heading'
        $('h1').text() == 'Course List'

        and: 'All courses are displayed correctly'
        def courses = $('#courseTable')
                .find('tbody')
                .children()

        courses.size() == 3

        courses.first().text() == '1 SESC Software Engineering for Service Computing 10.0'
        courses.getAt(1).text() == '2 AMT Advanced Music Theory 12.0'
        courses.last().text() == '3 PHSC Philosophy of Science 20.0'

        and: 'The correct text is displayed when I click the button'
        assert withConfirm(true) { $('#okButton').click() } == "Thank you for visiting"
    }
}
