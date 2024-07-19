package com.zam.uanet.services.imp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.dtos.StudentFull;
import com.zam.uanet.dtos.StudentNickBio;
import com.zam.uanet.dtos.UserDto;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.repositories.StudentRepository;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoClient mongoClient;

    @Override
    public StudentDTO createStudent(StudentEntity student) {
        if(student == null) {
            log.warn("El estudiante es nulo");
            throw new BadRequestException("El estudiante es nulo");
        }
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
        studentDTO.setFriends(studentEntity.getFriends());
        studentDTO.setBiografia(studentEntity.getBiografia());
        studentDTO.setIntereses(studentEntity.getIntereses());
        studentDTO.setHobbies(studentEntity.getHobbies());
        studentDTO.setNickname(studentEntity.getNickname());
        log.info("Se creo el estudiante con exito");
        return studentDTO;
    }

    @Override
    public StudentFull getStudent(ObjectId idStudent) {
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
        StudentFull studentFull = new StudentFull();
        studentFull.setIdStudent(studentEntity.getIdStudent().toHexString());

        UserEntity user = userRepository.findById(studentEntity.getIdUser()).get();
        UserDto userDto = new UserDto();
        userDto.setIdUser(user.getIdUser().toHexString());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRol(user.getRol());
        studentFull.setUserDto(userDto);
        studentFull.setFullname(studentEntity.getFullname());
        studentFull.setFecha_nacimiento(studentEntity.getFecha_nacimiento());
        studentFull.setGenre(studentEntity.getGenre());
        studentFull.setDistrito(studentEntity.getDistrito());
        studentFull.setCarreraProfesional(studentEntity.getCarreraProfesional());
        studentFull.setPhoto(studentEntity.getPhoto());
        studentFull.setBiografia(studentEntity.getBiografia());
        studentFull.setIntereses(studentEntity.getIntereses());
        studentFull.setHobbies(studentEntity.getHobbies());
        studentFull.setNickname(studentEntity.getNickname());
        return studentFull;
    }

    @Override
    public List<StudentDTO> listStudent() {
        List<StudentEntity> listaEstudiantes = studentRepository.findAll();
        if (listaEstudiantes.isEmpty()) {
            log.warn("La lista de estudiantes esta vacia");
            throw new NotFoundException("La lista de estudiantes esta vacia");
        }
        log.info("Lista de estudiantes obtenida! - {} estudiantes en total", listaEstudiantes.size());
        List<StudentDTO> listaClientesDto = new ArrayList<>();
        for (int i=0; i<listaEstudiantes.size(); i++) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setIdStudent(listaEstudiantes.get(i).getIdStudent().toHexString());
            studentDTO.setIdUser(listaEstudiantes.get(i).getIdUser().toHexString());
            studentDTO.setFullname(listaEstudiantes.get(i).getFullname());
            studentDTO.setFecha_nacimiento(listaEstudiantes.get(i).getFecha_nacimiento());
            studentDTO.setGenre(listaEstudiantes.get(i).getGenre());
            studentDTO.setDistrito(listaEstudiantes.get(i).getDistrito());
            studentDTO.setCarreraProfesional(listaEstudiantes.get(i).getCarreraProfesional());
            studentDTO.setPhoto(listaEstudiantes.get(i).getPhoto());
            studentDTO.setNickname(listaEstudiantes.get(i).getNickname());
            listaClientesDto.add(studentDTO);
        }
        return listaClientesDto;
    }

    @Override
    public StudentFull updateStudent(StudentEntity student) {
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
        StudentFull studentFull = new StudentFull();
        studentFull.setIdStudent(studentUpdated.getIdStudent().toHexString());
        UserEntity user = userRepository.findById(studentUpdated.getIdUser()).get();
        UserDto userDto = new UserDto();
        userDto.setIdUser(user.getIdUser().toHexString());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRol(user.getRol());
        studentFull.setUserDto(userDto);
        studentFull.setFullname(studentUpdated.getFullname());
        studentFull.setFecha_nacimiento(studentUpdated.getFecha_nacimiento());
        studentFull.setGenre(studentUpdated.getGenre());
        studentFull.setDistrito(studentUpdated.getDistrito());
        studentFull.setCarreraProfesional(studentUpdated.getCarreraProfesional());
        studentFull.setPhoto(studentUpdated.getPhoto());
        studentFull.setBiografia(studentUpdated.getBiografia());
        studentFull.setIntereses(studentUpdated.getIntereses());
        studentFull.setHobbies(studentUpdated.getHobbies());
        studentFull.setNickname(studentUpdated.getNickname());
        return studentFull;
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
    public StudentEntity findByUserQuery(ObjectId idUser) {
        return studentRepository.findByUserQuery(idUser);
    }

    @Override
    public byte[] findPhotoById(ObjectId idStudent) {
        MongoCollection<Document> collection = mongoClient.getDatabase("uanetbd").getCollection("students");
        // Consulta para filtrar por idStudent y proyectar solo el campo 'photo'
        Document result = collection.find(Filters.eq("_id", idStudent))
                .projection(Projections.fields(Projections.include("photo")))
                .first();
        // Verifica si se encontró un documento y retorna el campo 'photo'

        if (result != null) {
            Binary photoBinary = result.get("photo", Binary.class);
            if (photoBinary != null) {
                return photoBinary.getData();
                // Obtiene el array de bytes de los datos binarios
            } else {
                // Manejo si el campo 'photo' es null
                return null;
            }
        } else {
            // Manejo si no se encontró el documento
            return null;
        }
    }

    @Override
    public StudentNickBio getStudentBioAndNickname(ObjectId idStudent) {
        MongoCollection<Document> collection = mongoClient.getDatabase("uanetbd").getCollection("students");
        Document query = new Document("_id", idStudent);
        Document projection = new Document("biografia", 1)
                .append("nickname", 1);

        Document result = collection.find(query)
                .projection(projection)
                .first();

        if (result != null) {
            String biografia = result.getString("biografia");
            String nickname = result.getString("nickname");
            return new StudentNickBio(biografia, nickname);
        }
        return null;
    }

    @Override
    public StudentEntity getStudentSearch(int page, int size, String Search) {
        return null;
    }
}
