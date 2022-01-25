package uk.ac.leedsbeckett.studentlab;

import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.leedsbeckett.studentlab.model.Course;
import uk.ac.leedsbeckett.studentlab.model.CourseRepository;
import uk.ac.leedsbeckett.studentlab.model.Student;
import uk.ac.leedsbeckett.studentlab.model.StudentRepository;

import java.sql.SQLException;
import java.util.Set;

@Configuration
public class MiscellaneousBeans {

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository, StudentRepository studentRepository) {
        return args -> {
            Course sesc = new Course();
            sesc.setTitle("SESC");
            sesc.setDescription("Software Engineering for Service Computing");
            sesc.setFee(10.00);

            Course rema = new Course();
            rema.setTitle("REMA");
            rema.setDescription("Reverse Engineering and Malware Analysis");
            rema.setFee(20.00);

            Course ase = new Course();
            ase.setTitle("SESC");
            ase.setDescription("Advanced Software Engineering");
            ase.setFee(15.00);

            Student thalita = new Student();
            thalita.setForename("Thalita");
            thalita.setSurname("Vergilio");
            thalita.setExternalStudentId("c9999999");
            thalita.setCoursesEnrolledIn(Set.of(sesc, rema));

            Student duncan = new Student();
            duncan.setForename("Duncan");
            duncan.setSurname("Mullier");
            duncan.setExternalStudentId("c2222222");
            duncan.setCoursesEnrolledIn(Set.of(sesc, ase));

            studentRepository.saveAllAndFlush(Set.of(thalita, duncan));
        };
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}