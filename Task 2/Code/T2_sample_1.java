package com.in28minutes.springboot.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public String showLoginPage(ModelMap model) {
        model.put("name", "in28Minutes");
        return "welcome";
    }

}
package com.in28minutes.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.service.StudentService;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/{studentId}/courses")
    public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }
}
package com.in28minutes.springboot.model;

import java.util.List;

public record Course(String id,
                     String name,
                     String description,
                     List<String> steps) {
}
package com.in28minutes.springboot.model;

import java.util.List;

public record Student(String id,
                      String name,
                      String description,
                      List<Course> courses) {
}
package com.in28minutes.springboot.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        UserDetails userDetails1 = createNewUser("admin1", "secret1");

        return new InMemoryUserDetailsManager(userDetails1);
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

        return User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.httpBasic(withDefaults());

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }

}
package com.in28minutes.springboot.service;

import java.util.ArrayList;
import java.util.List;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.model.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private static final List<Student> students = new ArrayList<>();

    static {
        Course courseOne = new Course("Course1", "Spring", "10 Steps",
                List.of("Learn Maven", "Import Project", "First Example", "Second Example"));

        Course courseTwo = new Course("Course2", "Spring MVC", "10 Examples",
                List.of("Learn Maven", "Import Project", "First Example", "Second Example"));

        Student ranga = new Student("Student1", "Ranga Karanam",
                "Hiker, Programmer and Architect", new ArrayList<>(List.of(courseOne, courseTwo)));

        students.add(ranga);
    }

    public Student retrieveStudent(String studentId) {
        return students.stream()
                .filter(student -> student.id().equals(studentId))
                .findAny()
                .orElse(null);
    }

    public List<Course> retrieveCourses(String studentId) {
        Student student = retrieveStudent(studentId);

        return student == null ? null : student.courses();

    }
}
package com.in28minutes.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServicesApplication.class, args);
    }
}

