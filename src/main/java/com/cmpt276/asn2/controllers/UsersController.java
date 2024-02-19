package com.cmpt276.asn2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpt276.asn2.models.Student;
import com.cmpt276.asn2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    @Autowired
    private StudentRepository StudentRepo;

    @GetMapping("/Display")
    public String getallStudents(Model model){
        System.out.println("Getting all students");
        
        List<Student> students = StudentRepo.findAll();

        model.addAttribute("std", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
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

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam Map<String, String> Student, HttpServletResponse response){
        System.out.println("Deleting Student with the Name: " + Student.get("name"));

        List<Student> student = StudentRepo.findByName(Student.get("name"));

        if (!student.isEmpty()) {

            StudentRepo.deleteAll(student);
            response.setStatus(204);
            return "students/success"; 
        }
        response.setStatus(400);
        return "students/failed";   
    }

    @GetMapping("/Delete")
    public String getDeletePage(Model model) {
        List<Student> allStudents = StudentRepo.findAll();
        model.addAttribute("std", allStudents);
        return "/students/delete";
    }

    @GetMapping("/Add")
    public String getAddPage(Model model) {
        List<Student> allStudents = StudentRepo.findAll();
        model.addAttribute("std", allStudents);
        return "/students/add";
    }

    
}
