package uk.ac.leedsbeckett.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double fee;
    @ManyToMany(mappedBy = "coursesEnrolledIn")
    @JsonIgnore
    Set<Student> studentsEnrolledInCourse;
}
