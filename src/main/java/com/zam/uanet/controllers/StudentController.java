package com.zam.uanet.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.dtos.StudentFull;
import com.zam.uanet.entities.StudentEntity;
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
            @RequestPart("idUser") String idUser
    ) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dateFormat.parse(fecha_nacimiento);
        StudentEntity student = new StudentEntity();
        student.setIdUser(new ObjectId(idUser));
        student.setFullname(fullname);
        student.setFecha_nacimiento(fecha);
        student.setGenre(genero);
        student.setDistrito(distrito);
        student.setCarreraProfesional(carreraProfesional);
        student.setFriends(new ArrayList<>());
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentFull getStudent(@PathVariable(value = "id") ObjectId idStudent) {
        return studentService.getStudent(idStudent);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDTO> listStudents() {
        return studentService.listStudent();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StudentFull updateStudent(
            @RequestPart("idStudent") String idStudent,
            @RequestPart("idUser") String idUser,
            @RequestPart("fullname") String fullname,
            @RequestPart("fecha_nacimiento") String fecha_nacimiento,
            @RequestPart("genero") String genero,
            @RequestPart("distrito") String distrito,
            @RequestPart("carreraProfesional") String carreraProfesional,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("biografia") String bio,
            @RequestPart("intereses") String listTags,
            @RequestPart("hobbies") String hobbies,
            @RequestPart("nickname") String nickname
    ) throws IOException, ParseException {
        System.out.println(photo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dateFormat.parse(fecha_nacimiento);
        StudentEntity student = new StudentEntity();
        student.setIdStudent(new ObjectId((idStudent)));
        student.setIdUser(new ObjectId(idUser));
        student.setFullname(fullname);
        student.setFecha_nacimiento(fecha);
        student.setGenre(genero);
        student.setDistrito(distrito);
        student.setCarreraProfesional(carreraProfesional);
        student.setPhoto(photo.getBytes());
        student.setBiografia(bio);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> lista = objectMapper.readValue(listTags, new TypeReference<List<String>>() {});
        student.setIntereses(lista);
        List<String> lista2 = objectMapper.readValue(hobbies, new TypeReference<List<String>>() {});
        student.setHobbies(lista2);
        student.setNickname(nickname);
        return studentService.updateStudent(student);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStudent(@PathVariable(value = "id") ObjectId idStudent) {
        return studentService.deleteStudent(idStudent);
    }

}
