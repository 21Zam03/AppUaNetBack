package com.zam.uanet.services.imp;

import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        if(user == null) {
            log.warn("El usuario es nulo");
            throw new BadRequestException("El el usuario es nulo");
        }
        log.info("Se creo el usuario con exito");
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUser(ObjectId idUser) {
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
        return userEntity;
    }

    @Override
    public List<UserEntity> listUser() {
        List<UserEntity> listaUsuarios = userRepository.findAll();
        if (listaUsuarios.isEmpty()) {
            log.warn("La lista de usuarios esta vacia");
            throw new NotFoundException("La lista de usuarios esta vacia");
        }
        log.info("Lista de usuarios obtenida! - {} usuarios en total", listaUsuarios.size());
        return listaUsuarios;
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
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
        log.info("Se actualizo el usuario con el id: {}", userUpdated.getIdUser());
        return userUpdated;
    }

    @Override
    public String deleteUser(ObjectId idUser) {
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
        return "Eliminacion con exito!";
    }

}
