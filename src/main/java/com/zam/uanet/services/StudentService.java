package com.zam.uanet.services;

import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.dtos.StudentFull;
import com.zam.uanet.entities.StudentEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface StudentService {

    public StudentDTO createStudent(StudentEntity student);
    public StudentFull getStudent(ObjectId idStudent);
    public List<StudentDTO> listStudent();
    public StudentFull updateStudent(StudentEntity student);
    public String deleteStudent(ObjectId idStudent);
    public StudentEntity findByUserQuery(ObjectId idUser);

}
