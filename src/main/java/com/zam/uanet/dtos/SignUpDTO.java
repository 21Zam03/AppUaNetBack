package com.zam.uanet.dtos;


import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    private StudentEntity student;
    private UserEntity user;

}
