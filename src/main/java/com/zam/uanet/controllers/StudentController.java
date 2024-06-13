package com.zam.uanet.controllers;

import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO createStudent(
            @RequestPart("fullname") String fullname,
            @RequestPart("fecha_nacimiento") String fecha_nacimiento,
            @RequestPart("genero") String genero,
            @RequestPart("distrito") String distrito,
            @RequestPart("carreraProfesional") String carreraProfesional,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("rol") String rol
    ) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dateFormat.parse(fecha_nacimiento);

        StudentEntity student = new StudentEntity();
        student.setFullname(fullname);
        student.setFecha_nacimiento(fecha);
        student.setGenre(genero);
        student.setDistrito(distrito);
        student.setCarreraProfesional(carreraProfesional);
        student.setFriends(new ArrayList<>());

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        user.setRol(rol);
        return studentService.loginAction(user, student);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudent(@PathVariable(value = "id") ObjectId idStudent) {
        return studentService.getStudent(idStudent);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentEntity> listStudents() {
        return studentService.listStudent();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(
            @RequestPart("idStudent") String idStudent,
            @RequestPart("idUser") String idUser,
            @RequestPart("fullname") String fullname,
            @RequestPart("fecha_nacimiento") String fecha_nacimiento,
            @RequestPart("distrito") String distrito,
            @RequestPart("carreraProfesional") String carreraProfesional,
            @RequestPart("photo") MultipartFile photo
    ) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dateFormat.parse(fecha_nacimiento);
        StudentEntity student = new StudentEntity();
        student.setIdStudent(new ObjectId((idStudent)));
        student.setIdUser(new ObjectId(idUser));
        student.setFullname(fullname);
        student.setFecha_nacimiento(fecha);
        student.setDistrito(distrito);
        student.setCarreraProfesional(carreraProfesional);
        student.setPhoto(photo.getBytes());
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStudent(@PathVariable(value = "id") ObjectId idStudent) {
        return studentService.deleteStudent(idStudent);
    }

}
