package com.cmpt276.asn2.models;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findByName(String name);
    List<Student> findByNameAndAge(String name, int age);
    Optional<Student> findByUid(int uid);
}
