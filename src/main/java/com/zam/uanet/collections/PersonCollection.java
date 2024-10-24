package com.zam.uanet.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Period;

@Document(collection = "persons")
@Data
@Builder
public class PersonCollection {

    @Id
    private ObjectId personId;
    private ObjectId userId;
    private String photoProfile;
    private String firstName;
    private String lastName;
    private String genre;
    private LocalDate birthDate;
    private String district;
    private String nickname;
    private String career;
    //private List<ObjectId> friends;
    //private String biography;
    //private List<String> interests;
    //private List<String> hobbies;
    //private Boolean isActive;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAge() {
        LocalDate today_date = LocalDate.now();
        Period period = Period.between(this.birthDate, today_date);
        return String.valueOf(period.getYears());
    }

}
