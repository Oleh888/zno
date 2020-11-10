package studentlist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import studentlist.model.Student;

import java.awt.print.Pageable;
import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    Student findByPib(String pib);

    List<Student> findAll(Pageable pageable);
}
