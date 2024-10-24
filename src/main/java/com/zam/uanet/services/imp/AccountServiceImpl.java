package com.zam.uanet.services.imp;

import com.zam.uanet.collections.PersonCollection;
import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.payload.dtos.BlobDto;
import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.payload.response.PersonStartResponse;
import com.zam.uanet.repositories.PersonRepository;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.AccountService;
import com.zam.uanet.services.FireBaseStorageService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    private final PersonRepository personRepository;
    private final FireBaseStorageService fireBaseStorageService;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(PersonRepository personRepository, FireBaseStorageService fireBaseStorageService,
                              UserRepository userRepository) {
        this.personRepository = personRepository;
        this.fireBaseStorageService = fireBaseStorageService;
        this.userRepository = userRepository;
    }


    @Override
    public PersonStartResponse getPerson(ObjectId personId) {
        PersonCollection personCollection = personRepository.findById(personId).orElseThrow(() -> {
            log.error("Person not found");
            return new NotFoundException("Person doest not exist");
        });

        return PersonStartResponse.builder()
                .personId(personCollection.getPersonId().toHexString())
                .fullName(personCollection.getFullName())
                .nickName(personCollection.getNickname())
                .birthdate(personCollection.getBirthDate())
                .genre(personCollection.getGenre())
                .district(personCollection.getDistrict())
                .career(personCollection.getCareer())
                .profilePhoto(personCollection.getPhotoProfile())
                .build();
    }

    @Override
    public MessageResponse changePhotoProfile(String email, MultipartFile file) throws Exception {
        UserCollection loggedUser = userRepository.findByEmail(email).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });

        PersonCollection personToUpdate = personRepository.findByUserId(loggedUser.getIdUser()).orElseThrow(() -> {
            log.error("Person doesn't exist");
            return new NotFoundException("Person doest not exist");
        });

        if(personToUpdate.getPhotoProfile() != null) {
            fireBaseStorageService.deleteFile(personToUpdate.getPersonId().toHexString(), "persons/");
        }

        BlobDto blobDto = fireBaseStorageService.uploadFile(file, "persons/", personToUpdate.getPersonId().toHexString());

        personToUpdate.setPhotoProfile(blobDto.getUrl());
        personRepository.save(personToUpdate);

        log.info("Person photo profile updated");
        return new MessageResponse("Person profile updated");

    }

}
