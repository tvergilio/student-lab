package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.*;

import java.time.LocalDate;

@Component
public class EnrolmentService {
    private final StudentRepository studentRepository;
    private final IntegrationService integrationService;

    public EnrolmentService(StudentRepository studentRepository, IntegrationService integrationService) {
        this.studentRepository = studentRepository;
        this.integrationService = integrationService;
    }

    public void enrolStudentInCourse(Student student, Course course) {
        student.enrolInCourse(course);
        studentRepository.save(student);
        integrationService.createCourseFeeInvoice(createInvoice(student, course));
    }

    private Invoice createInvoice(Student student, Course course) {
        Account account = new Account();
        account.setStudentId(student.getExternalStudentId());
        Invoice invoice = new Invoice();
        invoice.setAccount(account);
        invoice.setType(Invoice.Type.TUITION_FEES);
        invoice.setAmount(course.getFee());
        invoice.setDueDate(LocalDate.now().plusMonths(1));
        return invoice;
    }
}
