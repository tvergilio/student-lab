package uk.ac.leedsbeckett.student;

import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.student.model.*;
import uk.ac.leedsbeckett.student.service.EnrolmentService;
import uk.ac.leedsbeckett.student.service.IntegrationService;

import java.sql.SQLException;
import java.util.Set;

@Configuration
public class MiscellaneousBeans {

    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository, IntegrationService integrationService, EnrolmentService enrolmentService) {
        return args -> {
            Course sesc = new Course();
            sesc.setTitle("SESC");
            sesc.setDescription("Software Engineering for Service Computing");
            sesc.setFee(10.00);

            Course rema = new Course();
            rema.setTitle("REMA");
            rema.setDescription("Reverse Engineering and Malware Analysis");
            rema.setFee(15.00);

            Course ase = new Course();
            ase.setTitle("ASE");
            ase.setDescription("Advanced Software Engineering");
            ase.setFee(20.00);

            Student thalita = new Student();
            thalita.setForename("Thalita");
            thalita.setSurname("Vergilio");
            thalita.setExternalStudentId("c9999999");
            thalita.enrolInCourse(sesc);
            thalita.enrolInCourse(rema);

            Student duncan = new Student();
            duncan.setForename("Duncan");
            duncan.setSurname("Mullier");
            duncan.setExternalStudentId("c2222222");
            duncan.enrolInCourse(sesc);
            duncan.enrolInCourse(ase);

            studentRepository.saveAllAndFlush(Set.of(thalita, duncan));
//            Account account = integrationService.getStudentAccount("c3781247");
//            Student tom = new Student();
//            tom.setForename("Tom");
//            tom.setSurname("Shaw");
//            tom.setExternalStudentId("c3781247");
//            studentRepository.save(tom);
//            enrolmentService.enrolStudentInCourse(tom, ase);
        };
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server h2Server() throws SQLException {
//        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//    }
}