package com.cmpt276.asn2.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpt276.asn2.models.Student;
import com.cmpt276.asn2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    @Autowired
    private StudentRepository StudentRepo;

    @GetMapping("/")
    public String getHomePage(){
        return "students/asn2";
    }

    @GetMapping("/Display")
    public String getallStudents(Model model){
        System.out.println("Getting all students");
        
        List<Student> students = StudentRepo.findAll();

        model.addAttribute("std", students);
        return "students/showAll";
    }

    @GetMapping("/Delete")
    public String getDeletePage(Model model) {
        List<Student> allStudents = StudentRepo.findAll();
        model.addAttribute("std", allStudents);
        return "students/delete";
    }

    @GetMapping("/Add")
    public String getAddPage(Model model) {
        List<Student> allStudents = StudentRepo.findAll();
        model.addAttribute("std", allStudents);
        return "students/add";
    }

    @GetMapping("/Edit")
    public String editDisplay(Model model){
        List<Student> allStudents = StudentRepo.findAll();
        model.addAttribute("std", allStudents);
        return "students/edit";
    }

    @PostMapping("/Add")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        System.out.println("ADD student");

        String newName = newStudent.get("name");
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor");
        double newGpa = Double.parseDouble(newStudent.get("gpa"));
        int newAge = Integer.parseInt(newStudent.get("age"));

        StudentRepo.save(new Student(newName, newWeight, newHeight, newHairColor, newGpa, newAge));
        response.setStatus(201);
        return "students/success";
    }

    @PostMapping("/Delete")
    public String deleteStudent(@RequestParam Map<String, String> Student, HttpServletResponse response){
        System.out.println("Deleting Student with the Name: " + Student.get("name") + " and Age: " + Student.get("age"));

        List<Student> getStudent = StudentRepo.findByNameAndAge(Student.get("name"), Integer.parseInt(Student.get("age")));

        if (!getStudent.isEmpty()) {

            Student student = getStudent.get(0);
            StudentRepo.delete(student);


            response.setStatus(200);
            return "students/success";

        } else {

            response.setStatus(400);
            return "students/failed"; 
        }  
    }
    
    @PostMapping("/Edit")
    public String editStudent(@RequestParam Map<String, String> Student, Model model, HttpServletResponse response){
        System.out.println("Editing Student with the Name: " + Student.get("name") + " and Age: " + Student.get("age"));

        List<Student> getStudent = StudentRepo.findByNameAndAge(Student.get("name"), Integer.parseInt(Student.get("age")));

        if (!getStudent.isEmpty()) {

            model.addAttribute("std", getStudent.get(0));

            response.setStatus(200);
            return "students/update";

        } else {

            response.setStatus(400);
            return "students/failed";
        }
    }

    @PostMapping("/Update")
    public String updateStudent(@ModelAttribute("std") Student student, HttpServletResponse response) {

        Optional<Student> updateStudent = StudentRepo.findByUid(student.getUid());

        if (updateStudent.isPresent()) {
            Student editStudent = updateStudent.get();
            editStudent.setName(student.getName());
            editStudent.setWeight(student.getWeight());
            editStudent.setHeight(student.getHeight());
            editStudent.setHairColor(student.getHairColor());
            editStudent.setGpa(student.getGpa());
            editStudent.setAge(student.getAge());

            StudentRepo.save(editStudent);
            return "students/success";
        }

        return "students/failed";
    }
}
