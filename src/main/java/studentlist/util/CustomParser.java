package studentlist.util;

import org.springframework.stereotype.Component;
import studentlist.model.Student;
import studentlist.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomParser {
    private static final String FILE_NAME = "test-data.txt";
    private final StudentRepository studentRepository;

    public CustomParser(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<List<Student>> getStudentsFromFile() {
        List<Student> students = null;
        try {
            students = Files.readAllLines(Paths.get(FILE_NAME)).stream()
            .map(this::getStudentFromLine)
            .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(students);
    }

    private Student getStudentFromLine(String line) {
        String[] info = line.split("\t");
        return new Student(info[1], Double.parseDouble(info[3]),
                            info[4].equals("До наказу (бюджет)"));
    }

    public List<Student> saveStudentsToDB(List<Student> students) {
        return studentRepository.saveAll(students);
    }
}
