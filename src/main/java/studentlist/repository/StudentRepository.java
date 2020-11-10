package studentlist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import studentlist.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
    Student findByPib(String pib);

    Page<Student> findByAvgMarkAfter(double avgMark, org.springframework.data.domain.Pageable pageable);

    Page<Student> findByAvgMarkBefore(double avgMark, org.springframework.data.domain.Pageable pageable);

    Page<Student> findByPib(String pib, org.springframework.data.domain.Pageable pageable);

}
