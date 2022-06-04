package com.ceydog.mobileactionbootcamp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`") //DB objects named with lower case letters for conventional purposes
public class User {
    @Id
    @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
    @GeneratedValue(generator = "User")
    @Column (name = "user_id", nullable = false)
    private Long userId;

    @Column (name = "first_name", length = 50, nullable = false)
    @NotNull
    private String firstName;

    @Column (name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column (name = "email_address", length = 50, nullable = false)
    private String emailAddress;

    @Column (name = "phone_number", length = 15, nullable = false)
    private String phoneNumber; //for formatting convenience

    @Temporal (TemporalType.DATE)
    private Date birthDate;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    /* Console notification - triggered when isActive status is changed.*/
    public void notifyActivity() {
        String activity = "passive";
        if (isActive)
            activity = "active";
        System.out.println("User " + userId + " is " + activity + "!");
    }

    public boolean getActive(){
        return isActive;
    }


}
