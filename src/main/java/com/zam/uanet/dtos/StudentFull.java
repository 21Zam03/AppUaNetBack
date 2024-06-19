package com.zam.uanet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFull {

    private String idStudent;
    private UserDto userDto;
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
