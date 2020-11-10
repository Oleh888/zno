package studentlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studentlist.model.Student;
import studentlist.repository.StudentRepository;
import studentlist.util.CustomParser;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    private final CustomParser parser;
    private final StudentRepository studentRepository;

    public StudentController(CustomParser parser, StudentRepository studentRepository) {
        this.parser = parser;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/saveStudents")
    public List<Student> getStudents() {
        List<Student> students = parser.getStudentsFromFile().orElse(new ArrayList<>());
        return parser.saveStudentsToDB(students);
    }

    @GetMapping("/love")
    public Student getLove(@RequestParam String pib) {
        return studentRepository.findByPib(pib);
    }
}
