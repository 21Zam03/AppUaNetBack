package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "students")
@Data
public class StudentEntity {

    @Id
    private ObjectId idStudent;
    private ObjectId idUser;
    private String fullname;
    private Date fecha_nacimiento;
    private String genre;
    private String distrito;
    private String carreraProfesional;
    private byte[] photo;
    private List<ObjectId> friends;
    private String biografia;
    private List<String> intereses;
    private List<String> hobbies;
    private String nickname;

}
