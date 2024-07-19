package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.dtos.StudentFull;
import com.zam.uanet.dtos.SignUpDTO;
import com.zam.uanet.dtos.UserDto;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.StudentService;
import com.zam.uanet.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;

    @Override
    public UserDto createUser(UserEntity user) {
        if(user == null) {
            log.warn("El usuario es nulo");
            throw new BadRequestException("El el usuario es nulo");
        }
        UserEntity userEntity = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setIdUser(userEntity.getIdUser().toHexString());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRol(userEntity.getRol());
        log.info("Se creo el usuario con exito");
        return userDto;
    }

    @Override
    public UserDto getUser(ObjectId idUser) {
        if(idUser == null) {
            log.warn("El id del usuario es nulo");
            throw new BadRequestException("El id del usuario es nulo");
        }
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(()-> {
                    log.warn("Usuario no encontrado con el id: {}", idUser);
                    return new NotFoundException("Usuario no encontrado con el id: "+idUser);
                });
        log.info("Se obtuvo el usuario con el id: {}", userEntity.getIdUser());
        UserDto userDto = new UserDto();
        userDto.setIdUser(userEntity.getIdUser().toHexString());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRol(userEntity.getRol());
        return userDto;
    }

    @Override
    public List<UserDto> listUser() {
        List<UserEntity> listaUsuarios = userRepository.findAll();
        if (listaUsuarios.isEmpty()) {
            log.warn("La lista de usuarios esta vacia");
            throw new NotFoundException("La lista de usuarios esta vacia");
        }
        List<UserDto> listaUsuariosDto = new ArrayList<>();
        for (UserEntity userEntity : listaUsuarios) {
            UserDto userDto = new UserDto();
            userDto.setIdUser(userEntity.getIdUser().toHexString());
            userDto.setEmail(userEntity.getEmail());
            userDto.setPassword(userEntity.getPassword());
            userDto.setRol(userEntity.getRol());
            listaUsuariosDto.add(userDto);
        }
        log.info("Lista de usuarios obtenida! - {} usuarios en total", listaUsuarios.size());
        return listaUsuariosDto;
    }

    @Override
    public UserDto updateUser(UserEntity user) {
        if (user.getIdUser() == null) {
            log.warn("El id del usuario a actualizar es nulo");
            throw new BadRequestException("El id del usuario a actualizar es nulo");
        }
        UserEntity userEntity = userRepository.findById(user.getIdUser())
                .orElseThrow(()-> {
                    log.warn("Usuario a actualizar no fue encontrado con el id: {}", user.getIdUser());
                    return new NotFoundException("Usuario a actualizar no fue encontrado con el id: "+user.getIdUser());
                });
        UserEntity userUpdated = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setIdUser(userUpdated.getIdUser().toHexString());
        userDto.setEmail(userUpdated.getEmail());
        userDto.setPassword(userUpdated.getPassword());
        userDto.setRol(userUpdated.getRol());
        log.info("Se actualizo el usuario con el id: {}", userUpdated.getIdUser());
        return userDto;
    }

    @Override
    public void deleteUser(ObjectId idUser) {
        if(idUser == null) {
            log.warn("El id del usuario a eliminar es nulo");
            throw new BadRequestException("El id del cliente a eliminar es nulo");
        }
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(()-> {
                    log.warn("Usuario a eliminar no fue encontrado con el id: {}", idUser);
                    return new NotFoundException("Usuario a eliminar no fue encontrado con el id: "+idUser);
                });
        userRepository.delete(userEntity);
        log.info("Se elimino el usuario con el id: {}", userEntity.getIdUser());
    }

    @Override
    public StudentFull loginAction(String email, String password) {
        UserEntity userEntity = userRepository.findByEmailAndPassword(email, password);
        if (userEntity != null) {
            UserDto userDto = new UserDto();
            userDto.setIdUser(userEntity.getIdUser().toHexString());
            userDto.setEmail(userEntity.getEmail());
            userDto.setPassword(userEntity.getPassword());
            userDto.setRol(userEntity.getRol());
            StudentEntity studentEntity = studentService.findByUserQuery(userEntity.getIdUser());
            StudentFull studentFull = new StudentFull();
            studentFull.setIdStudent(studentEntity.getIdStudent().toHexString());
            studentFull.setUserDto(this.getUser(studentEntity.getIdUser()));
            studentFull.setFullname(studentEntity.getFullname());
            studentFull.setFecha_nacimiento(studentEntity.getFecha_nacimiento());
            studentFull.setGenre(studentEntity.getGenre());
            studentFull.setDistrito(studentEntity.getDistrito());
            studentFull.setCarreraProfesional(studentEntity.getCarreraProfesional());
            studentFull.setPhoto(studentEntity.getPhoto());
            studentFull.setFriends(studentEntity.getFriends());
            studentFull.setBiografia(studentEntity.getBiografia());
            studentFull.setIntereses(studentEntity.getIntereses());
            studentFull.setHobbies(studentEntity.getHobbies());
            studentFull.setNickname(studentEntity.getNickname());
            return studentFull;
        } else {
            return new StudentFull();
        }
    }

    @Override
    public StudentFull signUpAction(SignUpDTO signUpDto) {
        UserEntity userEntity = signUpDto.getUser();
        UserDto userDto = this.createUser(userEntity);

        StudentEntity studentEntity = signUpDto.getStudent();
        studentEntity.setIdUser(new ObjectId(userDto.getIdUser()));
        StudentDTO studentDTO = studentService.createStudent(studentEntity);

        StudentFull studentFull = new StudentFull();
        studentFull.setIdStudent(studentDTO.getIdStudent());
        studentFull.setUserDto(userDto);
        studentFull.setFullname(studentDTO.getFullname());
        studentFull.setFecha_nacimiento(studentDTO.getFecha_nacimiento());
        studentFull.setGenre(studentDTO.getGenre());
        studentFull.setDistrito(studentDTO.getDistrito());
        studentFull.setCarreraProfesional(studentDTO.getCarreraProfesional());
        studentFull.setPhoto(studentDTO.getPhoto());
        studentFull.setFriends(studentDTO.getFriends());
        studentFull.setBiografia(studentDTO.getBiografia());
        studentFull.setIntereses(studentDTO.getIntereses());
        studentFull.setHobbies(studentDTO.getHobbies());
        studentFull.setNickname(studentDTO.getNickname());
        return studentFull;
    }

}
