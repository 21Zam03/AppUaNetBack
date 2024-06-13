package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.repositories.StudentRepository;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public StudentEntity createStudent(StudentEntity student) {
        if(student == null) {
            log.warn("El estudiante es nulo");
            throw new BadRequestException("El estudiante es nulo");
        }
        log.info("Se creo el estudiante con exito");
        return studentRepository.save(student);
    }

    @Override
    public StudentDTO getStudent(ObjectId idStudent) {
        if(idStudent == null) {
            log.warn("El id del estudiante es nulo");
            throw new BadRequestException("El id del estudiante es nulo");
        }
        StudentEntity studentEntity = studentRepository.findById(idStudent)
                .orElseThrow(()-> {
                    log.warn("Estudiante no encontrado con el id: {}", idStudent);
                    return new NotFoundException("Estudiante no encontrado con el id: "+idStudent);
                });
        log.info("Se obtuvo el estudiante con el id: {}", studentEntity.getIdStudent());
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setIdStudent(studentEntity.getIdStudent().toHexString());
        studentDTO.setIdUser(studentEntity.getIdUser().toHexString());
        studentDTO.setFullname(studentEntity.getFullname());
        studentDTO.setFecha_nacimiento(studentEntity.getFecha_nacimiento());
        studentDTO.setGenre(studentDTO.getGenre());
        studentDTO.setDistrito(studentEntity.getDistrito());
        studentDTO.setCarreraProfesional(studentEntity.getCarreraProfesional());
        studentDTO.setPhoto(studentEntity.getPhoto());
        return studentDTO;
    }

    @Override
    public List<StudentEntity> listStudent() {
        List<StudentEntity> listaEstudiantes = studentRepository.findAll();
        if (listaEstudiantes.isEmpty()) {
            log.warn("La lista de estudiantes esta vacia");
            throw new NotFoundException("La lista de estudiantes esta vacia");
        }
        log.info("Lista de estudiantes obtenida! - {} estudiantes en total", listaEstudiantes.size());
        return listaEstudiantes;
    }

    @Override
    public StudentDTO updateStudent(StudentEntity student) {
        if (student.getIdStudent() == null) {
            log.warn("El id del estudiante a actualizar es nulo");
            throw new BadRequestException("El id del estudiante a actualizar es nulo");
        }
        StudentEntity studentEntity = studentRepository.findById(student.getIdStudent())
                .orElseThrow(()-> {
                    log.warn("Estudiante a actualizar no fue encontrado con el id: {}", student.getIdStudent());
                    return new NotFoundException("Cliente a actualizar no fue encontrado con el id: "+student.getIdStudent());
                });
        StudentEntity studentUpdated = studentRepository.save(student);
        log.info("Se actualizo el estudiante con el id: {}", studentUpdated.getIdStudent());
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setIdStudent(studentUpdated.getIdStudent().toHexString());
        studentDTO.setIdUser(studentUpdated.getIdUser().toHexString());
        studentDTO.setFullname(studentUpdated.getFullname());
        studentDTO.setFecha_nacimiento(studentUpdated.getFecha_nacimiento());
        studentDTO.setGenre(studentUpdated.getGenre());
        studentDTO.setDistrito(studentUpdated.getDistrito());
        studentDTO.setCarreraProfesional(studentUpdated.getCarreraProfesional());
        studentDTO.setPhoto(studentUpdated.getPhoto());
        return studentDTO;
    }

    @Override
    public String deleteStudent(ObjectId idStudent) {
        if(idStudent == null) {
            log.warn("El id del estudiante a eliminar es nulo");
            throw new BadRequestException("El id del estudiante a eliminar es nulo");
        }
        StudentEntity studentEntity = studentRepository.findById(idStudent)
                .orElseThrow(()-> {
                    log.warn("Estudiante a eliminar no fue encontrado con el id: {}", idStudent);
                    return new NotFoundException("Estudiante a eliminar no fue encontrado con el id: "+idStudent);
                });
        studentRepository.delete(studentEntity);
        log.info("Se elimino el estudiante con el id: {}", studentEntity.getIdStudent());
        return "Eliminacion con exito!";
    }

    @Override
    public StudentDTO findByUserQuery(ObjectId idUser) {
        StudentEntity studentEntity = studentRepository.findByUserQuery(idUser);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setIdStudent(studentEntity.getIdStudent().toHexString());
        studentDTO.setIdUser(studentEntity.getIdUser().toHexString());
        studentDTO.setFullname(studentEntity.getFullname());
        studentDTO.setFecha_nacimiento(studentEntity.getFecha_nacimiento());
        studentDTO.setGenre(studentEntity.getGenre());
        studentDTO.setDistrito(studentEntity.getDistrito());
        studentDTO.setCarreraProfesional(studentEntity.getCarreraProfesional());
        studentDTO.setPhoto(studentEntity.getPhoto());
        return studentDTO;
    }

    @Override
    public StudentDTO loginAction(UserEntity user, StudentEntity student) {
        if(user == null || student == null) {
            log.warn("Estudiante o usuario son nulos");
            throw new BadRequestException("Estudiante o usuario son nulos");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntity = userRepository.save(user);
        student.setIdUser(userEntity.getIdUser());
        StudentEntity studentEntity = studentRepository.save(student);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setIdStudent(studentEntity.getIdStudent().toHexString());
        studentDTO.setIdUser(studentEntity.getIdUser().toHexString());
        studentDTO.setFullname(studentEntity.getFullname());
        studentDTO.setFecha_nacimiento(studentEntity.getFecha_nacimiento());
        studentDTO.setGenre(studentEntity.getGenre());
        studentDTO.setDistrito(studentEntity.getDistrito());
        studentDTO.setCarreraProfesional(studentEntity.getCarreraProfesional());
        studentDTO.setPhoto(studentEntity.getPhoto());
        log.info("Se creado un usuario con perfil de estudiante correctamente");
        return studentDTO;
    }

}
