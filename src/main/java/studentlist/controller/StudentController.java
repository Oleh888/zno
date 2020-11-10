package studentlist.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studentlist.model.Student;
import studentlist.repository.StudentRepository;
import studentlist.util.CustomParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> getAllStudentsPage(
            @RequestParam(required = false) String pib,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Student> students = new ArrayList<>();
            PageRequest paging = PageRequest.of(page, size);

            Page<Student> pageStudents;
            if (pib == null) {
                pageStudents = studentRepository.findAll(paging);
            } else {
                pageStudents = studentRepository.findByPib(pib, paging);
            }

            students = pageStudents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("students", students);
            response.put("currentPage", pageStudents.getNumber());
            response.put("totalElements", pageStudents.getTotalElements());
            response.put("totalPages", pageStudents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/studentsByMarkAfter")
    public ResponseEntity<Map<String, Object>> getAllStudentsWithAvgMarkAfter(
            @RequestParam(required = false) Double avgMark,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Student> students = new ArrayList<>();
            PageRequest paging = PageRequest.of(page, size);

            Page<Student> pageStudents;
            if (avgMark == null) {
                pageStudents = studentRepository.findAll(paging);
            } else {
                pageStudents = studentRepository.findByAvgMarkAfter(avgMark, paging);
            }

            students = pageStudents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("students", students);
            response.put("currentPage", pageStudents.getNumber());
            response.put("totalElements", pageStudents.getTotalElements());
            response.put("totalPages", pageStudents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/studentsByMarkBefore")
    public ResponseEntity<Map<String, Object>> getAllStudentsWithAvgMarkBefore(
            @RequestParam(required = false) Double avgMark,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Student> students = new ArrayList<>();
            PageRequest paging = PageRequest.of(page, size);

            Page<Student> pageStudents;
            if (avgMark == null) {
                pageStudents = studentRepository.findAll(paging);
            } else {
                pageStudents = studentRepository.findByAvgMarkBefore(avgMark, paging);
            }

            students = pageStudents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("students", students);
            response.put("currentPage", pageStudents.getNumber());
            response.put("totalElements", pageStudents.getTotalElements());
            response.put("totalPages", pageStudents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
