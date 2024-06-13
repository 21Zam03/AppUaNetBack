package com.zam.uanet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String idStudent;
    private String idUser;
    private String fullname;
    private Date fecha_nacimiento;
    private String genre;
    private String distrito;
    private String carreraProfesional;
    private byte[] photo;

}
