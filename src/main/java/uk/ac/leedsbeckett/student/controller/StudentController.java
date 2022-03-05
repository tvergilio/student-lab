package uk.ac.leedsbeckett.student.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.service.StudentService;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/api/students/{id}")
    @ResponseBody
    public EntityModel<Student> getStudentJson(@PathVariable Long id) {
        return studentService.getStudentByIdJson(id);
    }

    @GetMapping("/students/{id}")
    public ModelAndView getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

}
