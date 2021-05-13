package com.imgstorer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "last_online")
    private LocalDate lastOnline;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String description;

}
