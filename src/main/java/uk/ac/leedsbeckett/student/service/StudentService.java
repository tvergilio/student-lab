package uk.ac.leedsbeckett.student.service;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.controller.StudentController;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public EntityModel<Student> getStudentByIdJson(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course with id " + id + " not found."));
        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class)
                .getStudentJson(student.getId())).withSelfRel());
    }

    public ModelAndView getStudentById(Long id) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject(studentRepository.findById(id).orElseThrow(RuntimeException::new));
        return modelAndView;
    }
}
