package com.zam.uanet.services;

import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface StudentService {

    public StudentEntity createStudent(StudentEntity student);
    public StudentDTO getStudent(ObjectId idStudent);
    public List<StudentEntity> listStudent();
    public StudentDTO updateStudent(StudentEntity student);
    public String deleteStudent(ObjectId idstudent);

    public StudentDTO findByUserQuery(ObjectId idUser);
    public StudentDTO loginAction(UserEntity user, StudentEntity student);
}
