package uk.ac.leedsbeckett.studentlab.model;

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
    Set<Student> studentsEnrolledInCourse;
}
