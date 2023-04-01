package com.itm.api.user.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "user_profile")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_generator")
    @SequenceGenerator(name = "user_id_seq_generator", sequenceName = "user_id_sequence", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String department;

    @Column
    private String officeName;

    @Column
    private String teamName;

    @Column
    private Integer floorNumber;

    @Column
    private String profilePicLocation;

    @Column
    private String profilePicName;

    @Column
    private UUID externalUuid;

}
