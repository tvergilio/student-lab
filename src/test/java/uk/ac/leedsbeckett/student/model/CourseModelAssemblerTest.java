package uk.ac.leedsbeckett.student.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CourseModelAssemblerTest {
    private Course course;

    @Autowired
    private CourseModelAssembler courseModelAssembler;

    @BeforeEach
    public void setUp() {
        String courseTitle = "EMB";
        String courseDescription = "Embryology";
        Long courseId = 1L;
        Double courseFee = 30.0;
        course = new Course();
        course.setId(courseId);
        course.setTitle(courseTitle);
        course.setDescription(courseDescription);
        course.setFee(courseFee);
    }

    @Test
    void testToModel_withValidId_ReturnsExpectedEntityModel() {
        EntityModel<Course> result = courseModelAssembler.toModel(course);
        assertThat(course.equals(result.getContent()));
        assertThat(result.getLinks().hasSize(2));
        assertThat(result.hasLink("http://localhost/courses/1"));
        assertThat(result.hasLink("http://localhost/courses"));
    }

    @Test
    void testToModel_withIdNull_ThrowsException() {
        course.setId(null);
        assertThrows(RuntimeException.class, () -> courseModelAssembler.toModel(course),
                "Exception was not thrown.");
    }

    @Test
    void testToModel_withIdZero_ThrowsException() {
        course.setId(0L);
        assertThrows(RuntimeException.class, () -> courseModelAssembler.toModel(course),
                "Exception was not thrown.");
    }

    @Test
    void testToModel_withNullArgument_ThrowsException() {
        assertThrows(RuntimeException.class, () -> courseModelAssembler.toModel(null),
                "Exception was not thrown.");
    }
}