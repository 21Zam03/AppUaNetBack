package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "students")
@Data
public class StudentEntity {

    @Id
    private ObjectId idStudent;
    private ObjectId idUser;
    private String fullname;
    private Date fecha_nacimiento;
    private String direccion;
    private String carreraProfesional;
    private byte[] photo;

}
